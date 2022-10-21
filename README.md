# org.shaneking.roc

ShaneKing Java Spring Library

## @

### @ConditionalOnProperty

```yaml
sk:
  roc:
    persistence:
      entity:
        cache:
          enabled: true
      simple:
        entity:
          enabled: false
    rr:
      async:
        enabled: true
      audit:
        enabled: true
      cache:
        enabled: true
      channel:
        enabled: true
      crypto:
        enabled: true
      dsz:
        enabled: true
      limiting:
        enabled: true
      tenant:
        enabled: true
      user:
        enabled: true
    zero:
      cache:
        transactional:
          enabled: true
      simple:
        cache:
          enabled: false
```

### @ConfigurationProperties

```yaml
sk:
  roc:
    zero:
      net:
        address:
          enabled: true
          quietly: true
          ext-hosts-path: DefaultNull,SomeLike(classpath:vhosts)
          ext-props-path: DefaultNull,SomeLike(classpath:vhosts.properties)
          vhosts:
            DefaultEmpty: SomeLikeBelow
            inet.address.test2: 44.44.44.45,44.44.44.46
```

### @Value ${

```yaml
sk:
  roc:
    persistence:
      entity:
        cache:
          enabled: true
      dao:
        cache:
          seconds: 180
    rr:
      async:
        enabled: true
      audit:
        async: true
        enabled: true
      cache:
        enabled: true
      channel:
        enabled: true
      crypto:
        enabled: true
      dsz:
        enabled: true
      limiting:
        enabled: true
      tenant:
        enabled: true
      user:
        enabled: true
    zero:
      cache:
        transactional:
          enabled: true
```

