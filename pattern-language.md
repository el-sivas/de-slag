
# Pattern Language

## Beans

|Pattern|Description|
|:------|:----------|
|DataBean     |A bean that contains business data.
|Controller     |Bean that services UI
|ApplicationBean     |DataBean, member of Application Data Model
|ApplicationBeanService     |Service to handle ApplicationBeans of one type
|ApplicationBeanServiceImpl     |Implementation of an ApplicationBeanService
|Service     |Interface of a Service any kind
|ServiceImpl     |Implementation of a Service
|PersistBean     |DataBean, member of Persist Data Model
|Dao     |Interface of an Data Archiving Object to hanlde ApplicationBeans of one type. Uses PersistBeans
|DaoImpl     |Implementation of a database related Dao
|xDaoImpl     |Implementation of a non database related Dao. x stands for type (i.e. Web, File) 
|Holder     |DataBean for transient data holding, not a facade.
|Utils     |Helper, only static access, no use of other logic components except Utils
|~~Support~~     |God class, for lazy hours. Prevent this, refactor this, just dont do this.

## Methods

### Legend

PB - ProgrammaticBean

DB - DataBean

|Pattern    |Response Type    |Memer of    |Description|
|:-|:-|:-|:-|
|.create    |DB    |PB     |Creates a new DataBean.
|.delete    |void    |PB    |Deletes a DataBean logically.
|.save    |void    |PB    |Saves or updates a DataBean.
|.load    |DB    |PB    |Loads a DataBean from Persist Layer.
|.validate    |void    |PB    |Checks something and throws Exception if not valid
|.isAvalid    |boolean    |PB    |Checks something and returns true if valid.
|...all    |void, Collection<T>    |PB    |does things to multiple objects
|~~.check~~    |(whatever)    |(wherever)    |Checks something, you will not understand what or how until you read the methods  code. Don't do this.
