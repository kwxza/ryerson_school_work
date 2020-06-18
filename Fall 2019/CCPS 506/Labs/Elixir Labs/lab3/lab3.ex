# Lab 3  
# Kasaine Kipusi
# Student #: 500936268

defmodule Lab3 do

    # Compare list's head to head of its tail.
    def firstTwo(list) do
       hd(list) == hd(tl(list))
    end

    # Check if dividing list length by 2 
    # has a remainder.
    def evenSize(list) do
        0 == rem(length(list), 2)
    end

    # Append head of list to its tail.
    def frontBack(list) do 
        tl(list) ++ [hd(list)]
    end

    # Append 99 to head of list and then 
    # append list's tail to result.
    def nextNineNine(list) do
        [hd(list)] ++ [99] ++ tl(list)
    end

    # Subtract "Hello" from list and compare 
    # length of result to original list's length.
    def sayHello(list) do
        length(list) != length(list -- ["Hello"])
    end

    # Verify list has only 2 elements which are numbers.
    def isCoord(list) do
        (length(list) == 2) and is_number(hd(list)) and is_number(hd(tl(list)))
    end

    # Making use of boolean expressions returning the 
    # value that decides the expression's result. 
    # Either side of the '||' expression may return 
    # depending on if "Hello" is present. Each side 
    # contains an '&&' expression that will return 
    # a list modified from the original when its 
    # "Hello" condition is true.
    def helloIfSo(list) do
        ("Hello" in list && (list -- ["Hello"]) ++ ["Hello"]) || ("Hello" not in  list && ["Hello"] ++ list)
    end

    # The function checks the remainder of dividing
    # the length of the input list by 3. 
    # If the remainder is 0 or the length of the list
    # is less than 3, the original list is returned.
    # If the remainder is 1, the tail of the list is
    # returned.
    # If the remainder is 2, the tail of the tail of
    # the original list is returned.
    def makeTriple(list) do
        (length(list) < 3 && list || rem(length(list), 3) == 0 && list) || (rem(length(list), 3) == 1 && length(list) > 3 && tl(list)) || (rem(length(list), 3) == 2 && length(list) > 3 && tl(tl(list)))
    end
end