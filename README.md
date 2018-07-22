# de-slag-dawn

    MODULE STACK
    -data   | -> model   persistance layer  
    -model               persistance abstraction layer  
    -logic  | -> model   all special logic (create, evalutate, validate, calc, ...), interfaces  
    -view   | -> logic   gui
    
    MODULES
    dawn-root             external dependency management
    dawn-central          central functions, no special implementations
        dawn-base-tools     ONLY util classes, usable from everywhere, only external dependencies
        dawn-central-data   persistance layer
        dawn-central-
