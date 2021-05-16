# org.shaneking.roc

ShaneKing Java Spring Library

## yaml default values

```yaml
sk:
  roc:
    zero:
      cache:
        transactional:
          enabled: false
      hello:
        cache:
          enabled: false
      net:
        address:
          enabled: true
          quietly: true
          ext-hosts-path: DefaultNull,SomeLike(classpath:vhosts)
          ext-props-path: DefaultNull,SomeLike(classpath:vhosts.properties)
          vhosts:
            DefaultEmpty: SomeLikeBelow
            inet.address.test2: 44.44.44.45,44.44.44.46
    persistence:
      entity:
        cache:
          enabled: false
      hello:
        entity:
          enabled: false
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

## ConditionalOnMissingBean

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
  
