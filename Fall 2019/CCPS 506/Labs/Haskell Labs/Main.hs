findBigger x y = if x > y then x else y

main = do

    putStrLn "Enter first number:"
    nStr <- getLine
    let num1 = (read nStr::Double)
    putStrLn "Enter second number:"
    nStr <- getLine
    let num2 = (read nStr::Double)
    let big = findBigger num1 num2
    putStrLn ("Larger: " ++ (show big))
    