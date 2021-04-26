drop table if exists t_hello_cacheable_entity;
drop table if exists t_hello_numbered_entity;
drop table if exists t_hello_tenanted_numbered_entity;


-- HelloCacheableEntityTest_createTableIfNotExistSql_null_o.txt
create table if not exists `t_hello_cacheable_entity` (
  `version` int not null default 0,
  `id` char(40) not null,
  `no` varchar(40) default '',
  `invalid` varchar(1) default 'N',
  `last_modify_date_time` varchar(20) default '',
  `last_modify_user_id` varchar(40) default '',
  primary key (`id`)
);

-- HelloNumberedEntityTest_createTableIfNotExistSql_null_o.txt
create table if not exists `t_hello_numbered_entity` (
  `version` int not null default 0,
  `id` char(40) not null,
  `invalid` varchar(1) default 'N',
  `last_modify_date_time` varchar(20) default '',
  `last_modify_user_id` varchar(40) default '',
  `no` varchar(40) default '',
  primary key (`id`)
);

create unique index if not exists u_idx_no on t_hello_numbered_entity(`no`);

-- HelloTenantedNumberedEntityTest_createTableIfNotExistSql_null_o.txt
create table if not exists `t_hello_tenanted_numbered_entity` (
  `version` int not null default 0,
  `id` char(40) not null,
  `invalid` varchar(1) default 'N',
  `last_modify_date_time` varchar(20) default '',
  `last_modify_user_id` varchar(40) default '',
  `no` varchar(40) default '',
  `tenant_id` varchar(40) default '',
  primary key (`id`)
);

create unique index if not exists u_idx_no_tenant_id on t_hello_tenanted_numbered_entity(`no`,`tenant_id`);


select * from t_hello_cacheable_entity;
select * from t_hello_numbered_entity;
select * from t_hello_tenanted_numbered_entity;


vacuum;
