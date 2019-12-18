import sqlite3


class Car:
    def __init__(self, car_id, name, driver_licence):
        self.car_id = car_id
        self.name = name
        self.driver_licence = driver_licence

    def getCarName(self):
        return self.name

    def setCarName(self, name):
        self.name = name


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
vehicle = Car(1, "miysuru", human.getLicence)
new_fine = Fine(1, vehicle.car_id, "pretty brutal demon killing")

conn = sqlite3.connect("Endterm.db")
cur = conn.cursor()

cur.execute("create table if not exists Drivers(driver_licence TEXT, f_name TEXT, s_name TEXT)")
cur.execute("create table if not exists Cars(car_id INTEGER, car_name INTEGER, driver_licence TEXT)")
cur.execute("create table if not exists Fines(fine_id INTEGER, car_id INTEGER, fine_desc TEXT)")

cur.execute("INSERT INTO Drivers VALUES('%s', '%s', '%s')" %(human.driver_licence, human.f_name, human.s_name))
conn.commit()
conn.close()

print('done')

