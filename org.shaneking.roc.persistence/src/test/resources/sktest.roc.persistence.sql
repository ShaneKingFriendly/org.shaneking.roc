drop table if exists t_cacheable_entity_prepare;
drop table if exists t_numbered_entity_prepare;
drop table if exists t_tenanted_numbered_entity_prepare;
drop table if exists skrp_distributed_locks;


-- CacheableEntityTest_createTableIfNotExistSql_null_o.txt
create table if not exists `t_cacheable_entity_prepare`
(
  `id` char
(
  40
) not null,
  `ver` int not null default 0,
  `dd` varchar
(
  40
) default 'N',
  `ivd` varchar
(
  1
) default 'N',
  `lm_dsz` varchar
(
  20
) default '',
  `lm_uid` varchar
(
  40
) default '',
  `no` varchar
(
  40
) default '',
  primary key
(
  `id`
)
  );


-- NumberedEntityTest_createTableAndIndexIfNotExistSql_null_o.txt
create table if not exists `t_numbered_entity_prepare`
(
  `id` char
(
  40
) not null,
  `ver` int not null default 0,
  `dd` varchar
(
  40
) default 'N',
  `ivd` varchar
(
  1
) default 'N',
  `lm_dsz` varchar
(
  20
) default '',
  `lm_uid` varchar
(
  40
) default '',
  `no` varchar
(
  40
) default '',
  primary key
(
  `id`
)
  );

create unique index if not exists u_idx_no on t_numbered_entity_prepare(`no`);


-- TenantedNumberedEntityTest_createTableAndIndexIfNotExistSql_null_o.txt
create table if not exists `t_tenanted_numbered_entity_prepare`
(
  `id` char
(
  40
) not null,
  `ver` int not null default 0,
  `dd` varchar
(
  40
) default 'N',
  `ivd` varchar
(
  1
) default 'N',
  `lm_dsz` varchar
(
  20
) default '',
  `lm_uid` varchar
(
  40
) default '',
  `no` varchar
(
  40
) default '',
  `tenant_id` varchar
(
  40
) default '',
  primary key
(
  `id`
)
  );

create unique index if not exists u_idx_tenant_id_no on t_tenanted_numbered_entity_prepare(`tenant_id`,`no`);


-- DistributedDbLock
create table if not exists `skrp_distributed_locks`
(
  `lock_key` char
(
  40
) not null,
  `lock_status` varchar
(
  40
) default 'N',
  `client_id` varchar
(
  40
) default '',
  `time_millis` long default 0,
  primary key
(
  `lock_key`
)
  );


select *
from t_cacheable_entity_prepare;
select *
from t_numbered_entity_prepare;
select *
from t_tenanted_numbered_entity_prepare;
select *
from skrp_distributed_locks;

vacuum;
