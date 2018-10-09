# de-slag-dawn

    A. LAYERS (target architecture) 
    ###############################

    1   UI
    2   Model
    3   Service
    4   ServiceImpl
    5   Data
    6   DataImpl

    A1. Dependencies (logical)
    ==========================
    dependency      type            understandment
    ----------      ----            --------------
    knows           logic-bean      knows them as payload
    uses            logic-logic     one logic uses the other
    implements      abstraction     interfaces abstract implmentations

    1   knows       2
    1   uses        3
    3   knows       2
    4   implements  3
    4   knows       2
    4   uses        5
    5   knows       2
    6   implements  5
    6   knows       2   

    
    B. MODULE STACK
    ###############
            
    MODULES         LAYERS              CONTAINS
    -------         ------              --------
    1 webapp        UI                   
    2 view          UI                  Controller, .xhtml
    3 model         Model, Service      Service, ApplicationBean
    4 logic         ServiceImpl, Data   ServiceImpl, Dao, ApplicationContext
    5 data          DataImpl            DaoImpl, PersistBean
    
    B1. Dependencies (technical)
    ============================    
    1 -> 2 -> 3 <- 4 <- 5
    | ----------------> |
    
        
    C. PATTERN LANGUAGE
    ###################
    Pattern                     Description
    -------                     -----------
    Controller                  Bean that services UI
    ApplicationBean             Member of Application Data Model
    ApplicationBeanService      Service to handle ApplicationBeans of one type
    ApplicationBeanServiceImpl  Implementation of an ApplicationBeanService
    Service                     Interface of a Service any kind
    ServiceImpl                 Implementation of a Service
    PersistBean                 Member of Persist Data Model
    Dao                         Interface of an Data Archiving Object to hanlde ApplicationBeans of one type. Uses PersistBeans
    DaoImpl                     Implementation of a database related Dao
    xDaoImpl                    Implementation of a non database related Dao. x stands for type (i.e. Web, File) 
    Utils                       Helper, only static access, no use of other logic components except Utils
