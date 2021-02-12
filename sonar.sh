#!/usr/bin/env bash

export sk_roc__sonar_dir=$(readlink -f "$(dirname "${BASH_SOURCE[0]}")")
if [ ${SK_EXP__SONAR__TOKEN} ]; then
  cd ${sk_roc__sonar_dir}
  mvn sonar:sonar -Dsonar.host.url=http://sonar.shaneking.org -Dsonar.login=${SK_EXP__SONAR__TOKEN}
  sk_roc_modules=("org.shaneking.roc.cache" "org.shaneking.roc.jackson" "org.shaneking.roc.persistence" "org.shaneking.roc.persistence.hello" "org.shaneking.roc.rr" "org.shaneking.roc.test" "org.shaneking.roc.zero")
  for sk_roc_module in ${sk_roc_modules[@]}; do
    if [ -d ${sk_roc__sonar_dir}/${sk_roc_module} ]; then
      cd ${sk_roc__sonar_dir}/${sk_roc_module}
      mvn sonar:sonar -Dsonar.host.url=http://sonar.shaneking.org -Dsonar.login=${SK_EXP__SONAR__TOKEN}
    else
      echo "${sk_roc__sonar_dir}/${sk_roc_module} not exist."
    fi
  done
else
  echo 'SK_EXP__SONAR__TOKEN not exist.'
fi
cd ${sk_roc__sonar_dir}
