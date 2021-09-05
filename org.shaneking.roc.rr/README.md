## 请求响应（Request and Response）报文格式定义

这只是一个通用模版，各系统可根据自身需要定制。

### Request

- `obj`：为请求的业务对象
- `jon`：为扩展的 `Json Object Node`
- `ext.tbl`：主要用于分页场景

```json
{
  "pub": {
    "channelNo": "渠道编号",
    "encoded": "Y|N。表示 pri 是否加密为 enc",
    "jon": null,
    "tenantNo": "租户编号。不传入则同 channelNo，即 tenantNo=channelNo",
    "tracingNo": "跟踪编号（一系列请求）。通常不传入",
    "reqNo": "请求编号（本次请求）。通常不传入"
  },
  "enc": "pri 的加密形式，或空，或无此节点。若 encoded 为 Y，且此节点为 pri 的加密形式，则 pri 节点可不存在，如果存在也会被 enc 解密出的结果覆盖",
  "pri": {
    "ext": {
      "dsz": "yyyy-MM-dd HH:mm:ss.SSSXXX",
      "jon": null,
      "tbl": {
        "pagination": {
          "count": 1,
          "page": 2,
          "size": 3
        }
      },
      "userNo": "用户编号"
    },
    "obj": null
  }
}
```

### Response

- `rtn`：为响应的业务对象

```json
{
  "code": "0表示正常。非0表示各种异常",
  "msg": "异常情况下关键信息。部分场景下该节点可能不存在，比如业务处理正常",
  "data": {
    "pub": {
      "channelNo": "原样返回",
      "encoded": "原样返回",
      "jon": null,
      "tenantNo": "原样返回 或为 channelNo 值",
      "tracingNo": "如请求未给，则生成唯一编号。否则原样返回",
      "reqNo": "如请求未给，则生成唯一编号。否则原样返回"
    },
    "enc": "pri 的加密形式，或空，或无此节点。若 encoded 为 Y，且此节点为 pri 的加密形式，则 pri 节点可不存在，如果存在，请使用 enc 解密出的结果覆盖",
    "pri": {
      "ext": {
        "dsz": "yyyy-MM-dd HH:mm:ss.SSSXXX",
        "jon": null,
        "tbl": {
          "pagination": {
            "count": 1,
            "page": 2,
            "size": 3
          }
        },
        "userNo": "原样返回"
      },
      "obj": null,
      "rtn": null
    }
  }
}
```

### Context

- `ctx`：为本次请求的上下文信息，适合于业务处理分层较少的六边形架构场景。`InheritableThreadLocal` 不适合部分线程池场景。`ThreadLocal` 不适合子线程场景。
  - `auditLog`：审计日志
  - `channel`：渠道对象
  - `jon`：扩展的 `Json Object Node`
  - `language`：语言。`zh-CN`，参考：<http://www.rfc-editor.org/rfc/bcp/bcp47.txt>
  - `tenant`：租户对象
  - `user`：用户对象
