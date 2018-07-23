# de-slag-dawn

    MODULE STACK
    -data   |                 persistance layer
    -logic  | -> model,data   all special logic (create, evalutate, validate, calc, ...), interfaces  
    -model                    data+logic abstraction layer  
    -view   | -> model        gui
