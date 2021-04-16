import hashlib
import os

path = os.getcwd()

with os.scandir(path) as listOfEntries:
    for entry in listOfEntries:
        if entry.is_file():
            with open(entry, "rb") as f:
                data = f.read()
                data = hashlib.sha3_256(data).hexdigest()
            print(f"{entry.name} {data}")