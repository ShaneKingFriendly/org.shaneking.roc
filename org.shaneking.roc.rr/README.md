## 请求响应（Request and Response）报文格式定义
### Context
- `ctx`：为本次请求的上下文信息，适合于业务处理分层较少的六边形架构场景。`InheritableThreadLocal` 不适合部分线程池场景。`ThreadLocal` 不适合子线程场景。
  - `auditLog`：审计日志
  - `channel`：渠道对象
  - `jon`：扩展的 `Json Object Node`
  - `language`：语言。`zh-CN`，参考：<http://www.rfc-editor.org/rfc/bcp/bcp47.txt>
  - `tenant`：租户对象
  - `user`：用户对象
