# de-slag-dawn

    MODULE STACK
    -data   persistance layer
    -logic  all special logic (create, evalutate, validate, calc, ...), interfaces  
    -model  data+logic abstraction layer  
    -view   webapp-gui
    
    data <- logic -> model <- view
    
    Bean Definitions:
    Service     singleton managed bean
    Util        no instances, only static methods
    Support     singleton, self managed, stateful bean
