
class Pathfinder():
    def __init__(self, vector):
        # Initialize the Pathfinder object
        self.vector = vector
        self.paths = []
        self.findAllPaths(0,[])

    def findAllPaths(self, position, solution):
        # Recursively explore the possible paths and store valid paths
        # This method will not be tested, so you can modify the parameters as needed
        solution.append(position)
        status = 0
        
        if position == len(self.vector)-1:  
            self.paths.append(solution.copy())
            #print(f"Good solution: {solution.copy()}")
            solution.pop()
            
            return 1
        elif position >= len(self.vector):
            solution.pop()
            return 0
        elif solution[:len(solution)-2].count(position) > 0:
            solution.pop()
            return 0 
        elif position < 0:
            solution.pop()
            return 0

        else:   
            status += self.findAllPaths(position+self.vector[position], solution)
            status += self.findAllPaths(position-self.vector[position], solution)
            solution.pop()
            return status

    def getPaths(self):
        # Return the list of viable paths, or [None] if there are no solutions
        return self.paths

# The Pathfinder class is initialized with a list of integers, the target of which is the position with value 0.
# The findAllPaths method recursively explores the list.
# The getPaths method returns the list of viable paths, or [None] if there are no solutions





if __name__ == "__main__":
 #   v = [2, 8, 3, 2, 7, 2, 2, 3, 2, 1, 3, 0]
#v = [2,3,1,1,0]
#v = [3, 1, 1, 1, 3, 4, 2, 5, 3, 0]
    v = [4,3,1,2,3,5,4,2,2,1,1,0]
    v= [4,4,1,2,3,1,8,2,0]
    pf = Pathfinder(v)
    print("Solving " + str(v))
    for p in pf.getPaths():
        print(p)


    if False:

        

            
        path = []
        paths = []
        print(v)

        #simplest path - check for the possibility of infinite loop
        def FindAllPaths(position, vector, solution):
            
            solution.append(position)
            status = 0
            
            if position == len(vector)-1:  
                paths.append(solution)
                print(f"Good solution: {solution.copy()}")
                solution.pop()
                
                return 1
            elif position >= len(vector):
                solution.pop()
                return 0
            elif solution[:len(solution)-2].count(position) > 0:
                solution.pop()
                return 0 
            elif position < 0: 
                solution.pop()
                return 0

            else:   
                status += FindAllPaths(position+vector[position], vector, solution)
                status += FindAllPaths(position-vector[position], vector, solution)
                solution.pop()
                return status


        c = FindAllPaths(0,v,path)
        if c == 0:
            print("No Solution")
        else:
            print(f"Found {c} solutions")
            


        
