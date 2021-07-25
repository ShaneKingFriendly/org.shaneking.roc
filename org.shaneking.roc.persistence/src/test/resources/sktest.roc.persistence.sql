drop table if exists t_cacheable_entity_prepare;
drop table if exists t_numbered_entity_prepare;
drop table if exists t_tenanted_numbered_entity_prepare;
drop table if exists skrp_distributed_locks;


-- CacheableEntityTest_createTableIfNotExistSql_null_o.txt
create table if not exists `t_cacheable_entity_prepare` (
  `version` int not null default 0,
  `id` char(40) not null,
  `dd` varchar(40) default 'N',
  `no` varchar(40) default '',
  `invalid` varchar(1) default 'N',
  `last_modify_date_time` varchar(20) default '',
  `last_modify_user_id` varchar(40) default '',
  primary key (`id`)
);

-- NumberedEntityTest_createTableAndIndexIfNotExistSql_null_o.txt
create table if not exists `t_numbered_entity_prepare` (
  `version` int not null default 0,
  `id` char(40) not null,
  `dd` varchar(40) default 'N',
  `no` varchar(40) default '',
  `invalid` varchar(1) default 'N',
  `last_modify_date_time` varchar(20) default '',
  `last_modify_user_id` varchar(40) default '',
  primary key (`id`)
);

create unique index if not exists u_idx_no on t_numbered_entity_prepare(`no`);

-- TenantedNumberedEntityTest_createTableAndIndexIfNotExistSql_null_o.txt
create table if not exists `t_tenanted_numbered_entity_prepare` (
  `version` int not null default 0,
  `id` char(40) not null,
  `dd` varchar(40) default 'N',
  `no` varchar(40) default '',
  `invalid` varchar(1) default 'N',
  `last_modify_date_time` varchar(20) default '',
  `last_modify_user_id` varchar(40) default '',
  `tenant_id` varchar(40) default '',
  primary key (`id`)
);

create unique index if not exists u_idx_no_tenant_id on t_tenanted_numbered_entity_prepare(`no`,`tenant_id`);


create table if not exists `skrp_distributed_locks` (
  `lock_key` char(40) not null,
  `lock_status` varchar(40) default 'N',
  `client_id` varchar(40) default '',
  `time_millis` long default 0,
  primary key (`lock_key`)
);


select * from t_cacheable_entity_prepare;
select * from t_numbered_entity_prepare;
select * from t_tenanted_numbered_entity_prepare;
select * from skrp_distributed_locks;

vacuum;
