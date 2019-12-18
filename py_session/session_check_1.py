import sqlite3


class Car:
    def __init__(self, car_id, car_name, driver_licence):
        self.car_id = car_id
        self.car_name = car_name
        self.driver_licence = driver_licence

    def getCarName(self):
        return self.car_name

    def setCarName(self, car_name):
        self.car_name = car_name


class Driver:
    def __init__(self, driver_licence, f_name, s_name, ):
        self.driver_licence = driver_licence
        self.f_name = f_name
        self.s_name = s_name

    def getFullName(self):
        return self.f_name + " " + self.s_name

    def getLicence(self):
        return self.driver_licence


class Fine:
    def __init__(self, fine_id, car_id, fine_desc, ):
        self.fine_id = fine_id
        self.car_id = car_id
        self.fine_desc = fine_desc



    def getFineId(self):
        return self.fine_id

    def setFineId(self):
        return self.fine_id


human = Driver("125512", "Mister", "Fastest")
vehicle = Car(1, "miysuru", human.getLicence())
new_fine = Fine(1, vehicle.car_id, "pretty brutal demon killing")

conn = sqlite3.connect("Endterm.db")
cur = conn.cursor()
'''
cur.execute("create table if not exists Drivers(driver_licence TEXT, f_name TEXT, s_name TEXT)")
cur.execute("create table if not exists Cars(car_id INTEGER, car_name INTEGER, driver_licence TEXT)")
cur.execute("create table if not exists Fines(fine_id INTEGER, car_id INTEGER, fine_desc TEXT)")

cur.execute("INSERT INTO Drivers VALUES('%s', '%s', '%s')" % (human.driver_licence, human.f_name, human.s_name))
cur.execute("INSERT INTO Cars VALUES('%d', '%s', '%s')" % (vehicle.car_id, vehicle.car_name, vehicle.driver_licence))
cur.execute("INSERT INTO Fines VALUES('%d', '%d', '%s')" % (new_fine.fine_id, new_fine.car_id, new_fine.fine_desc))

conn.commit()

dvs = cur.execute("select * from Drivers").fetchall()
crs = cur.execute("select * from Cars").fetchall()
fns = cur.execute("select * from Fines").fetchall()
print(dvs)
print(crs)
print(fns)
print('done')
conn.close()'''

with open("library.txt", 'r+') as txtFile:
    library = list(txtFile)

print(library)