#
#   Tester for CCPS305 Assignment 1
#   Usage: python(3) TrieTester.py libname
#   You will need the 'american-english-no-accents' file in the directory of the tester


import random
import string
import re
# Module Imports
import string
import sys
from importlib import import_module

wordlist = []


def GenerateWord(size):
    chars = string.ascii_letters+"'"
    word = ""
    for i in range(0, random.randint(size/2, size)):
        word += random.choice(chars)
    return word


def Test(lib, seed=0, size=10, rounds=10, verbose=False, wordlist=[]):

    if not lib:
        print("You need to include a testable library")
        return False

    if not wordlist:
        f = open("american-english-no-accents.txt", "r")
        readwords = f.readlines()
        for word in readwords:
            wordlist.append(word)

    random.seed(a=seed)

    trie = lib.MyTrie()

    for word in wordlist:
        trie.insert(word)

    if len(wordlist) != len(trie):
        if verbose:
            print("Trie size mismatch!")
        return False

    if verbose:
        print("Insertion test complete")
    yield True

    for j in range(0, rounds):
        expected = True
        c = ""

        if random.randint(0, 2) == 0:
            c = GenerateWord(6)
        else:
            c = wordlist[random.randint(0, len(wordlist))]
        expected = c in wordlist
        res = trie.exists(c)
        if res != expected:
            if verbose:
                print("Word " + c + " was " + ("found" if res else "not found") +
                      " and was expected to " + ("" if expected else "not") + " be.")
            return False
    if verbose:
        print("Trie item test complete")
    yield True

    try:
        mlist = trie.autoComplete("")
    except:
        if verbose:
            print("Error: Autocomplete method not callable")
        return False

    if not(mlist == wordlist):
        if verbose:
            print("Error: Autocomplete missing items for empty list")
        return False

    if verbose:
        print("Complete retrieval test complete")
    yield True

    try:
        emptyList = trie.autoComplete("fasdfasdfasdfasfawef")
    except:
        if verbose:
            print("Error: Autocomplete does not respond to unmatched prefix")
        return False

    if len(emptyList) != 0:
        if verbose:
            print("Error: Autocomplete returning items for unmatchable prefix")
        return False

    if verbose:
        print("Unmatchable prefix test complete")
    yield True

    for j in range(0, rounds):
        w = ""
        while len(w) < 4:
            w = random.choice(wordlist)
        prefix = w[0:3]

        matchlist = list(filter(lambda x: x.startswith(prefix), wordlist))
        try:
            autocompletelist = trie.autoComplete(prefix)
        except:
            if verbose:
                print("Error: Autocomplete method not callable")
            return False

        # Check more problems? ie not in order, in order but missing, etc
        if not (matchlist == autocompletelist):
            print(matchlist)
            print()
            print()
            print(autocompletelist)
            if verbose:
                print("Error: Autocomplete method not returning correct information")
            return False

    if verbose:
        print("Autocomplete test complete")
    yield True


if __name__ == "__main__":
    try:
        f = open("american-english-no-accents.txt", "r")
    except:
        print("Error: Please ensure the wordlist file is in this directory.")
        exit()
    readwords = f.readlines()
    for word in readwords:
        wordlist.append(word.rstrip())

    if len(sys.argv) < 2:
        print("Include at least a library name as an argument.")
        exit()
    name = sys.argv[1]
    if name.endswith(".py"):
        name = name[:-3]
    print(f"Testing module {name}")
    module = import_module(name, package=__name__)
    score = 0
    for i in Test(module, seed=123456, size=20, verbose=True, wordlist=wordlist):
        if i:
            score += 1
        else:
            break
    print(f"Test result: {score}/5")
