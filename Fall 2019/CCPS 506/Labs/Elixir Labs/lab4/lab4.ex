defmodule Lab4 do
    
    # I define private functions for each function 
    # that will pattern match the argument signatures
    # during the recursion.

    # For each function except reduce/3, the function 
    # that the user calls has an arity of 1, while
    # the private functions that do the recursion
    # take multiple arguments that are set within
    # the original call to func/1 .

    # sumEven/1 - the function accessible to the user.
    def sumEven(list) do sumEven(list, 0) end
    # Once the list is empty (fully iterated),
    # return the sum.
    defp sumEven([], sum) do sum end
    # sumEven/2 does the work of calculating
    # the sum of even numbers in the list.
    defp sumEven(list, sum) do
        [h|tail] = list
        # I'm setting sum equal to the result of
        # the if macro because it seems like the 
        # value is not saved if I do sum = sum + h
        # within the if block.
        # When I try to do so, the compiler tells
        # me that the sum variable is not being 
        # used. 
        # It seems to me that Elixir's if macro 
        # behaves more like the ternary operator 
        # (?) in Java than the if statement in Java. 
        sum = if (is_integer(h) and rem(h,2) == 0) do 
            sum + h
            else sum
        end
        sumEven(tail, sum)
    end

    # sumNum/1 - user-accessible function
    def sumNum(list) do sumNum(list, 0) end
    defp sumNum([], sum) do sum end
    # The logic and structure of sumNum/2 is
    # very similar to sumEven/2.
    # However I use case to choose what values
    # to use for the parameters to the next
    # recursive call.
    defp sumNum(list, sum) do
        case list do
            # The list item will only be added to the sum
            # if it is a number.
            [h|tail] when is_number(h) -> sumNum(tail, sum + h)
            [_|tail] -> sumNum(tail, sum)
        end
    end

    # tailFib/1 - the function available to the user.
    def tailFib(n) do 
        # Check here if the input is valid.
        # If so, do initial recursive call to tailFib/3.
        cond do
            is_integer(n) and n >= 0 -> tailFib(n, 0, 0)
            true -> "Input must be a positive integer!!" 
        end
    end
    # Once @stepsLeft has been reduced to zero,
    # the resulting Fibonacci number can be returned.
    defp tailFib(0, fib, _lastFib) do fib end

    # @stepsLeft - number of iterations left to
    #              calculate nth Fibonacci number
    # @fib - most recent Fibonacci number calculated 
    # @lastFib - previous Fibonacci number calculated
    # Within the first call to tailFib/3, 
    # fib is set to 1 (first Fibonacci number).
    # For each recursive call after that, the values of
    # stepLeft, fib and lastFib are set as they are passed
    # as parameters to the next level of recursion.
    defp tailFib(stepsLeft, fib, lastFib) do
        case fib do
            0 -> tailFib(stepsLeft - 1, 1, 0)
            _ -> tailFib(stepsLeft - 1, fib + lastFib, fib)
        end
    end


    # The reduce() function does not use private functions
    # for its recursion. This is because the compiler gives
    # me a compilation error when I try to define private
    # functions with the same name as a public function 
    # that has a default argument. 
    # I'm not sure why this occurs.

    # reduce/3 - this function has an arity of 3, but the
    # default argument allows the user to enter only
    # two arguments (list and func).
    # If no acc value is defined, the accumulator
    # will be set to the head of the list.
    def reduce(list, acc \\ nil, func)

    # Base case once there are no more list
    # items left to reduce.
    def reduce([], acc, _func) do acc end

    # A guard clause checks if the accumulator 
    # is still the default value (nil).
    # If so, then reduce/3 is called with the
    # accumulator set to the head of the list and
    # the list set to its own tail.
    def reduce(list, acc, func) when is_nil(acc) do
        reduce(tl(list), hd(list), func)
    end

    # Control passes to the below function immediately
    # if the user has set the acc value, or on 2nd 
    # recursive call to reduce/3 if the acc value 
    # was unset (nil).
    def reduce([head|tail], acc, func) do
        reduce(tail, func.(head, acc), func)
    end

end