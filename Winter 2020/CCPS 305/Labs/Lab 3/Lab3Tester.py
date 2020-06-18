# give frequency list
# give text string, get bitstring
# give bitstring, get text string
import random
import string
import re
#Module Imports
import sys
from importlib import import_module

def GenerateFreqs(size=10):
    if (size <= 0) :
        size = 1
    elif (size > 26):
        size = 26
        
    freqs = {}
    letters=string.ascii_letters[:-26] + " '"
    chars = []
    for i in range(size):
            char=random.choice(letters)
            letters = letters.replace(char,"")
            freqs[char]=random.randint(0,size*2)
            chars.append(char)
    
    return chars, freqs

def GenerateWord(chars, size):
    word=""
    for i in range(0, random.randint(size/2, size)):
        word += random.choice(chars)
    return word
    

def Test(lib, seed=123456, size=10, rounds=10, verbose=False ):
    if not lib:
        print("You need to include a testable library")
        return False
    random.seed(a=seed)
    

    # Known string test -> 0 or 1 for long branch irrelevant, but length with be the same
    freqs = {'a': 15, 'd': 9, 'e': 18, 'H': 10, 'l': 16, 'o': 7, 'W': 4, 'r': 11, 'z': 3, ' ': 20}
    s="Hello World"
    try:
        huff = lib.MyHuffman()
    except:
        if verbose:
            print("Error: Tree not creatable")
        return False
    if verbose:
        print(f"Building tree from {freqs}")
    try:
        huff.build(freqs)
    except:
        if verbose:
            print("Error: Frequecy building not completable")
        return False
    
    if verbose:
        print(f"Encoding string \"{s}\"")
    try:
            bs = huff.encode(s)
    except:
        if verbose:
            print("Error: Word not encodable")
        return False
    if verbose:
        print(f"Received \"{bs}\"")
    if len(bs) != 38:
        if verbose:
            print("Error: Huffman algorithm not compressing properly")
        return False
    if verbose:
        print(f"Decoding back to original")
    try:
            rs = huff.decode(bs)
    except:
        if verbose:
            print("Error: Bitstring not decodable")
        return False
    if rs != s:
        if verbose:
            print(f"Error: Decoding incorrect. Expected {s} but got {rs}")
        return False
    if verbose:
        print(f"Correctly received \"{rs}\" from decoding \"{bs}\" from encoding \"{s}\"")

    for i in range(rounds):
        [chars, freqs]=GenerateFreqs(size)
        if verbose:
            print()
            print("Round " + str(i+1))
            print ("Current frequencies are: " + str(freqs))
        if not freqs:
            return None
        
        try:
            huff = lib.MyHuffman()
        except:
            if verbose:
                print("Error: Tree not creatable")
            return False
        try:
            huff.build(freqs)
        except:
            if verbose:
                print("Error: Frequecy building not completable")
            return False
        
        words = {}
        
        for j in range(1, rounds+1):
            word = GenerateWord(chars, j*2)
            bitstring = ""
            try:
                bitstring = huff.encode(word)
            except:
                if verbose:
                    print("Error: Word not encodable")
                return False
            if not re.search("^[0,1]+$",bitstring):
                if verbose:
                    print("Encoded string does not contain only 0 or 1 : " + bitstring)
                return False
            words[word] = bitstring

        for word, bitstring in words.items():
            decrypted = ""
            try:
                decrypted = huff.decode(bitstring)
            except:
                if verbose:
                    print("Error: Word not decodable")
                return False
            #if verbose:
               # if j == rounds:
               #     print(f"{word} -> {bitstring} -> {decrypted}")
            if not(word == decrypted):
                if verbose:
                    print(f"Error: {decrypted} should be {word}")
                return False

        #Big test efficieny check
        charsSpread = ""
        for char,frequency in freqs.items():

           for i in range(0,frequency):
               charsSpread += char
        
        paragraph = ""
        for i in range(0,size*10):
            paragraph += random.choice(charsSpread)
        
        try:
            paragraphBitString = huff.encode(paragraph)
        except:
            if verbose:
                print("Error: Paragraph not encodable")
            return False

        if not re.search("^[0,1]+$",paragraphBitString):
            if verbose:
                print("Encoded paragraph does not contain only 0 or 1 : " +paragraphBitString)
            return False
        try:
            paragraphUncompressed = huff.decode(paragraphBitString)
        except:
            if verbose:
                print("Error: Paragraph not decodable")
            return False
        if id(paragraph) == id(paragraphUncompressed):
            if verbose:
                print(f"Error: Returned string is the same as original.")
            return False
            
        if not(paragraph == paragraphUncompressed):
            if verbose:
                print(f"Error: {decrypted} should be {word}")
            return False
        
        if verbose:
            pSize = len(paragraph) * 8
            pBSSize = len(paragraphBitString)
            perc = (pBSSize/pSize) * 100
            print("Paragraph compression reduced size to " + "{:.1f}".format(perc) + "% of the original")

    return True

    
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
    v = Test(module,seed=123456, size=1000, rounds=50, verbose=True)
    print(f"Test result: {v}")