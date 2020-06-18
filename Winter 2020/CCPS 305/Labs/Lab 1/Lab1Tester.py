#
#   Tester for CCPS305 Lab1
#   Usage: python(3) Lab1Tester.py libname
#   Updated: 24-10-2019
#

#Module Imports
import string
import sys
from importlib import import_module

#Testing Imports
import random

def Test(lib, seed=0, size=10, rounds=10, verbose=False):
    if not lib:
        print("You need to include a testable library")
        return False

    random.seed(a=seed)

    try:
        queue = lib.MyQueue()
        queue
    except:
        if verbose:
            print("Error: Queue initialization incomplete")
        return False

    r=[]
    
    #Stress test
    for j in range(size):
        n=random.randint(0,size)
        r.append(n)
        try:
            queue.enqueue(n)
        except:
            if verbose:
                print("Error: Queue push incomplete.")
            return False
        
    try:
        int(len(queue))
    except:
        if verbose:
            print("Error: Queue __len__ method not returning integer")
        return False            

    if(len(queue) != size):
        if verbose:
            print("Error: Queue size should be " + str(size) + " but is " + str(len(queue)))
        return False
    
    if verbose:
        print("Queue size test complete")
    yield True

    for j in range(size):
        try:
            q=queue.dequeue()
        except:
            if verbose:
                print("Error: Queue retrieval incomplete")
            return False

        if r[j] != q:
            if verbose:
                print("Error: Queue value should be " + str(r[j]) + " but is " + str(q) )
            return False
    

    if verbose:
        print("Queue value test complete")
    yield True


    try:
        stack = lib.MyStack()
        stack
    except:
        if verbose:
            print("Error: Stack initialization incomplete")
        return False

    r = []

    for j in range(size):
        n=random.randint(0,size)
        r.append(n)
        try:
            stack.push(n)       
        except:
            if verbose:
                print("Error: Stack push incomplete.")
            return False

    try:
        int(len(stack))
    except:
        if verbose:
            print("Error: Stack __len__ method not returning integer")
        return False

    if verbose:
        print("Stack size test complete")
    yield True

    if(len(stack) != size):
        if verbose:
            print("Error: Stack size should be " + str(size) + " but is " + str(len(stack)))
        return False

    for j in range(size):
        try:
            s=stack.pop()
        except:
            if verbose:
                print("Error: Sack retrieval incomplete")
            return False
        if r[size-j-1] != s:
            if verbose:
                print("Error: Stack value should be " + str(r[size-j-1]) + " but is " + str(s) )
            return False
    #End stress test

    
    if verbose:
        print("Stack value test complete")
    yield True

    # Check that queues are different objects, ensure non-loss of head node
    m = random.randint(0,size)
    n = random.randint(0,size)
    o = random.randint(0,size)
    
    try:
        queue.enqueue(m)
        queue2 = lib.MyQueue(n)
        queue2.enqueue(o)
    except:
        if verbose:
            print("Error: Queue operations incorrect")
        return False
    
    try:
        stack.push(m)
        stack2 = lib.MyStack(n)
        stack2.push(o)
    except:
        if verbose:
            print("Error: Stack operations incorrect")
        return False
    try:
        nQ = queue2.dequeue()
        nQ
    except:
        return False
    if(nQ != n):
        if verbose:
            print(f"Error: Queue retrieval should be {n} but is {nQ}")
        return False

    try:
        oS = stack2.pop()
        oS
    except:
        return False
    if(oS != o):
        if verbose:
            print(f"Error: Stack retrieval should be {o} but is {oS}")
        return False

    if(queue.dequeue() != m):
        if verbose:
            print("Error: Queue instancing incomplete.")
        return False
    if(stack.pop() != m):
        if verbose:
            print("Error: Stack instancing incomplete.")
        return False
    
    if verbose:
        print("Object instantiation test complete")
    yield True

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Include at least a library name as an argument.")
        exit()
    name = sys.argv[1]
    if name.endswith(".py"):
        name = name[:-3]
    print(f"Testing module {name}")
    module=import_module(name,package=__name__)
    score = 0
    for i in Test(module,seed=123456, size=3000, rounds=200, verbose=True):
        if i:
            score+=1
        else:
            break
    print(f"Test result: {score}/5")
