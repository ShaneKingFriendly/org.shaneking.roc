package org.shaneking.roc.jackson;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.shaneking.ling.jackson.databind.OM3;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public interface JavaTypeResolvable {

  default JavaType[] resolveRtnJavaTypes(ProceedingJoinPoint pjp) {
    Method method = resolveMethod(pjp);
    JavaType javaType = resolveJavaType(ResolvableType.forMethodReturnType(method).getType(), pjp.getTarget().getClass());
    return resolveJavaTypes(javaType);
  }

  default JavaType[] resolveArgJavaTypes(ProceedingJoinPoint pjp, int idx) {
    Method method = resolveMethod(pjp);
    JavaType javaType = resolveJavaType(ResolvableType.forMethodParameter(method, idx).getType(), pjp.getTarget().getClass());
    return resolveJavaTypes(javaType);
  }

  default JavaType[] resolveJavaTypes(JavaType javaType) {
    JavaType[] rtnJavaTypes = new JavaType[javaType.containedTypeCount()];
    for (int i = 0; i < javaType.containedTypeCount(); i++) {
      rtnJavaTypes[i] = javaType.containedType(i);
    }
    return rtnJavaTypes;
  }

  default JavaType resolveJavaType(Type type, Class<?> contextClass) {
    TypeFactory typeFactory = OM3.om().getTypeFactory();
    if (contextClass != null) {
      ResolvableType resolvableType = ResolvableType.forType(type);
      if (type instanceof TypeVariable) {
        ResolvableType resolvableVariable = resolveVariable((TypeVariable<?>) type, ResolvableType.forClass(contextClass));
        if (resolvableVariable != ResolvableType.NONE) {
          return typeFactory.constructType(resolvableVariable.resolve());
        }
      } else if (type instanceof ParameterizedType && resolvableType.hasUnresolvableGenerics()) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Class<?>[] generics = new Class<?>[parameterizedType.getActualTypeArguments().length];
        Type[] argTypes = parameterizedType.getActualTypeArguments();
        for (int i = 0; i < argTypes.length; i++) {
          Type argType = argTypes[i];
          if (argType instanceof TypeVariable) {
            ResolvableType resolvableVariable = resolveVariable((TypeVariable<?>) argType, ResolvableType.forClass(contextClass));
            generics[i] = resolvableVariable != ResolvableType.NONE ? resolvableVariable.resolve() : ResolvableType.forType(argType).resolve();
          } else {
            generics[i] = ResolvableType.forType(argType).resolve();
          }
        }
        return typeFactory.constructType(ResolvableType.forClassWithGenerics(resolvableType.getRawClass(), generics).getType());
      }
    }
    return typeFactory.constructType(type);
  }

  default ResolvableType resolveVariable(TypeVariable<?> typeVariable, ResolvableType contextType) {
    ResolvableType rtnResolvableType = null;
    if (contextType.hasGenerics()) {
      rtnResolvableType = ResolvableType.forType(typeVariable, contextType);
      if (rtnResolvableType.resolve() != null) {
        return rtnResolvableType;
      }
    }

    ResolvableType superType = contextType.getSuperType();
    if (superType != ResolvableType.NONE) {
      rtnResolvableType = resolveVariable(typeVariable, superType);
      if (rtnResolvableType.resolve() != null) {
        return rtnResolvableType;
      }
    }

    for (ResolvableType interfaceType : contextType.getInterfaces()) {
      rtnResolvableType = resolveVariable(typeVariable, interfaceType);
      if (rtnResolvableType.resolve() != null) {
        return rtnResolvableType;
      }
    }

    return ResolvableType.NONE;
  }

  default Method resolveMethod(ProceedingJoinPoint pjp) {
    Method rtnMethod = null;
    String methodLongString = pjp.getSignature().toLongString();
    for (Method method : pjp.getSignature().getDeclaringType().getMethods()) {
      if (method.toString().equals(methodLongString)) {
        rtnMethod = method;
        break;
      }
    }
    return rtnMethod;
  }
}
