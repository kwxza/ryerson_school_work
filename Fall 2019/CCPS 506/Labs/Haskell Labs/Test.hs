module Test where

    fm m = case m of
        Nothing -> 0
        Just x -> x

    book = [
        ("Alex", 555),
        ("Bob", 533),
        ("John", 421) ]

    safeTail x
        | (length x > 0) = Just (tail x)
        | otherwise = Nothing

    safeHead x
        | (length x > 0) = Just (head x)
        | otherwise = Nothing

    getMaybeVal (Just a) = a

    testPos numString = do
        let x = read numString::Double
        if x < 0 then False else True

    positive = do
        putStr "Enter a number: "
        num <- getLine
        return ((read num::Double) < 0)

    data Pt = Pt3 Float Float Float
            | Pt2 Float Float
            -- deriving (Show)

    instance Show Pt where
        show (Pt2 x y) =
            "<" ++ (show x) ++ ", " ++ (show y) ++ ">"

    instance Eq Pt where
        (Pt2 x1 y1) == (Pt2 x2 y2) = (x1==x2 && y1==y2)
        (Pt2 x1 y1) /= (Pt2 x2 y2) = not (x1==x2 && y1==y2)

    ptX (Pt2 x _) = x
    ptX (Pt3 x _ _) = x

    ptY (Pt2 _ y) = y
    ptY (Pt3 _ y _) = y

    ptZ (Pt3 _ _ z) = z

    chkClr rgb = 
        case rgb of
            (255,_,_) -> "RED"
            (_,255,_) -> "GREEN"
            (_,_,255) -> "BLUE"
            (_,_,_) -> "NONE"

    -- chkAxis :: (Float, Float) -> (Float, Float)
    chkAxis (0,_) = (0,1)
    chkAxis (_,0) = (1,0)
    chkAxis (a,b) = (a,b)

    roots a b c = 
        let disc = sqrt(b*b - 4*a*c)
        in ( (-b + disc)/(2*a),
             (-b - disc)/(2*a))

    pos x = x >= 0

    filt p [] = []
    filt p (xh:xt) = 
        if p xh then xh : filt p xt
        else filt p xt

    llen [] = 0
    llen (xh:xt) = 1 + (llen xt)

    cmp2 x y | x < y = "First is smaller"
             | x > y = "Second is smaller"
             | otherwise = "Equal"

    fac 0 = 1
    fac x = x*fac(x-1)

    fib 0 = 0
    fib 1 = 1
    fib n = fib(n-1) + fib(n-2)

    isNum x =
        case x of
            0 -> 0
            1 -> 1
            2 -> 2
            _ -> -1

    sign x = do
        let q = x
        if x < 0 then -1
        else if x > 0 then 1
        else 0