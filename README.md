# org.shaneking.roc

ShaneKing Java Spring Library

## @

### @ConditionalOnMissingBean

- persistence
  - ApiAccessRegexEntities(**recommended**)
  - ApiAccessSignatureEntities(optional)
  - ApiAccessUrlEntities(**recommended**)
  - AuditLogEntities(`required`)
  - ChannelEntities(`required`)
  - ChannelReadableTenantEntities(optional)
  - TenantEntities(`required`)
  - TenantReadableTenantEntities(optional)
  - UserEntities(`required`)
- rr
  - RrAutoCreateUserService(optional)
- zero
  - ZeroCache(optional)

### @ConditionalOnProperty

```yaml
sk:
  roc:
    persistence:
      entity:
        cache:
          enabled: false
      simple:
        entity:
          enabled: false
    rr:
      audit:
        enabled: true
      access:
        enabled: true
      cache:
        enabled: true
      crypto:
        enabled: true
      limiting:
        enabled: true
    zero:
      chche:
        transactional:
          enabled: false
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
          enabled: false
      dao:
        cache:
          seconds: 180
    rr:
      limiting:
        enabled: true
      audit:
        enabled: true
      access:
        enabled: true
      cache:
        enabled: true
      crypto:
        enabled: true
```

