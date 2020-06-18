module Lab5 where

    -- Recursive call on the tail of input list
    -- until its length is 3 and then pattern
    -- match the 1st element (third from last)
    thirdLast [x, _, _] = x
    thirdLast (inputHead:inputTail) = thirdLast inputTail 

    -- Using tail recursively to skip every other 
    -- element in the input list and using cons to 
    -- connect the desired elements into a list
    everyOther [] = []
    everyOther (inputHead:inputTail) = 
        inputHead : everyOther (tail inputTail)
    
    -- Filtering and then folding the input list
    sumPosList [] = 0
    sumPosList input = foldl (+) 0 (filter (>=0) input) 
