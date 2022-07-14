### To get list of directories: 
  - listFiles() of java.io.File
  - walk(path) of java.nio.file.Files
are being used here.
---
### Common things:
- both listFiles() & walk(path) return the list of directories.
- If I am correct, both use depth search first.
- That's why we are getting "test" directory first in nested-level 1 
---
### Differences:
- walk(path) method of java.nio.file.Files should be enclosed with try-with resource block.
- while using listFiles() of java.io.File try block is not mandatory.
- Here, we are getting the relative paths of the files/directories using walk(path) method.
- And we are getting just the directories name using listFiles().
- So, we need both methods.
---
### note:
- All methods will work for any directories.
- As for now, hard coded the path as "src" however it won't change the fact of 1st point.
- Common CLI are yet to be implemented

