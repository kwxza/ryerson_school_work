import random
import string
import re
#Module Imports
import sys
from importlib import import_module


# give h(x), g(x)
# give data
# expect certain hits
# check for infinite loop
# retrieve data, give data, retrieve
# ensure correct data

def GenerateWord(size):
    word=""
    for i in range(0, random.randint(round(size/2), size)):
        word += random.choice(string.ascii_letters)
    return word

def Test(lib, seed=0, size=10, rounds=10, verbose=False ):
    if not lib:
        print("You need to include a testable library")
        return False
    random.seed(a=seed)
    
    PRIME1 = 127
    PRIME2 = 113
    hash1 = lambda a: a % PRIME1
    hash2 = lambda a: PRIME2-(a%PRIME2)

    storage={}
    n = random.randint(1,PRIME1)
    m = n+PRIME1
    w = GenerateWord(5)
    v = GenerateWord(6)
    storage[n]=w
    storage[m]=v
    

    try:
        table = lib.MyHashTable(PRIME1, hash1)
    except:
        if verbose:
            print("Error: Table not creatable")
        return False

    try:
        if not table.put(n,storage[n]):
            if verbose:
                print("Error: HashTable collision in empty table")
            return False    
    except:
        if verbose:
            print("Error: HashTable not insertable")
        return False
    

    try:
        if table.put(m,storage[m]):
            if verbose:
                print("Error: HashTable collision not detected")
            return False
    except:
        if verbose:
            print("Error: HashTable error during collision")
        return False
    
    try:
        nW = table.get(n)
    except:
        if verbose:
            print("Error: HashTable data not retrievable")
        return False
    
    if nW != storage[n]:
        if verbose:
            if nW == storage[m]:
                print("Error: HashTable collision replaces previous data")
            else:
                print("Error: HashTable retrieval incorrect")
        return False

    c = 1
    for i in range(2,PRIME1 + 10):
        if table.put(i,""):
            c += 1
        if len(table) != c:
            if verbose:
                print("Error: HashTable size incorrect")
            return False

    try:
        if table.get(n+(2*PRIME1)) is not None:
            if verbose:
                print("Error: HashTable get incorrect")
            return False
    except:
        if verbose:
            print("Error: HashTable not retrievable")
        return False

    try:
        if not table.isFull():
            if verbose:
                print(f"Error: Full table not detected.")
            return False
    except:
        if verbose:
            print("Error: HashTable isFull incorrect")
        return False 

    if verbose:
        print("HashTable test complete")
    yield True # Basic HashTable tested

    try:
        chain = lib.MyChainTable(PRIME1, hash1)
    except:
        if verbose:
            print("Error: ChainTable not creatable")
        return False

    try:
        if not chain.put(n, storage[n]):
            if verbose:
                print("Error: ChainTable collision in empty table")
            return False
    except:
        if verbose:
            print("Error: ChainTable not insertable")

    try:
        chain.put(m, storage[m])
    except:
        if verbose:
            print("Error: ChainTable not insertable")
    
    try:
        nW = chain.get(n)
        mW = chain.get(m)
    except:
        if verbose:
            print("Error: ChainTable not retrievable")
        return False

    if nW == storage[m]:
        if verbose:
            print("Error: ChainTable collision overwrites previous data")
        return False

    if nW != storage[n] or mW != storage[m]:
        if verbose:
            print("Error: ChainTable retrieval incorrect")
        return False

    try:
        if len(chain) != 2:
            if verbose:
                print("Error: ChainTable length incorrect")
            return False
    except:
        if verbose:
            print("Error: ChainTable length inaccessible")

    try:
        if chain.get(n+(2*PRIME1)) is not None:
            if verbose:
                print("Error: ChainTable get incorrect")
            return False
    except:
        if verbose:
            print("Error: ChainTable not retrievable")
        return False

    c = 2
    for i in range(1, PRIME1+10):
        if chain.put(i,"E"):
            c+= 1
        if len(chain)!=c:
            if verbose:
                print("Error: ChainTable length incorrect") 
            return False
        
    if chain.isFull():
        if verbose:
            print("Error: ChainTable isFull incorrect")
        return False

    if verbose:
        print("ChainTable test complete")
    yield True

    try:
        double = lib.MyDoubleHashTable(PRIME1, hash1, hash2)
    except:
        if verbose:
            print("Error: DoubleHashTable not creatable")
        return False

    try:
        if not double.put(n,storage[n]):
            if verbose:
                print("Error: DoubleHashTable collision in empty table")
            return False    
    except:
        if verbose:
            print("Error: DoubleHashTable not insertable")
        return False
    

    try:
        if not double.put(m,storage[m]):
            if verbose:
                print("Error: DoubleHashTable collision not detected")
            return False
    except:
        if verbose:
            print("Error: DoubleHashTable error during collision")
        return False

    try:
        nW = double.get(n)
    except:
        if verbose:
            print("Error: DoubleHashTable data not retrievable #1")
        return False
    
    if nW != storage[n]:
        if verbose:
            if nW == storage[m]:
                print("Error: HashTable collision replaces previous data")
            else:
                print("Error: HashTable retrieval incorrect")
        return False

    try:
        mW = double.get(m)
    except:
        if verbose:
            print("Error: DoubleHashTable data not retrievable #2")
        return False

    if mW != storage[m]:
        if verbose:
            if nW == storage[n]:
                print("Error: HashTable retrieval hashing incorrect")
            else:
                print("Error: HashTable retrieval incorrect")
        return False

    try:
        if len(double) != 2:
            if verbose:
                print("Error: DoubleHashTable length incorrect")
            return False
    except:
        if verbose:
            print("Error: DoubleHashTable length not accessible")
        return False

    if verbose:
        print("DoubleHashTable simple test complete")
    yield True

    for i in range(1,PRIME1+10):
        if not double.put(i,f"{i}"): # Code might hang here
            break
    
    if i+1 != PRIME1:
        if verbose:
            print("Error: DoubleHashTable rejects data from non-full table")
        return False

    if i+1 != len(double):
        if verbose:
            print("Error: DoubleHashTable length incorrect")
        return False
    
    if verbose:
        print("DoubleHashTable loop test complete")
    yield True

    for j in range(1,i):
        if double.get(j) is None:
            if verbose:
                print("Error: DoubleHashTable hashing incorrect")
            return False
        elif double.get(n) != storage[n]:
            if verbose:
                print("Error: DoubleHashTable overwriting values during collision")
            return False

    if verbose:
        print("DoubleHashTable completeness test complete")
    yield True

 
if __name__ == "__main__":
    print(len("hello"))
    if len(sys.argv) < 2:
        print("Include at least a library name as an argument.")
        exit()
    name = sys.argv[1]
    if name.endswith(".py"):
        name = name[:-3]
    print(f"Testing module {name}")
    module=import_module(name,package=__name__)
    score=0
    for i in Test(module,seed=123456, size=1000, rounds=200, verbose=True):
        if i:
            score+=1
        else:
            break

    print(f"Test result: {score}/5")