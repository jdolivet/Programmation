# -*- coding: utf-8 -*-

# 6.00.2x Problem Set 4

import numpy
import random
import pylab
from patientWithViruses import *


#
# PROBLEM 1
#
def simulationWithDrugDelayed(numViruses, maxPop, maxBirthProb, clearProb,
                              resistances, mutProb, numTrials, delayedSteps):
    resultstot = [[] for k in range(delayedSteps + 150)]
    for l in range(numTrials):
        viruses = []
        for k in range(numViruses):
            viruses.append(ResistantVirus(maxBirthProb, clearProb,
                                          resistances, mutProb))
        patient = TreatedPatient(viruses, maxPop)
        for m in range(delayedSteps + 150):
            if m == delayedSteps:
                patient.addPrescription("guttagonol")
            patient.update()
            resultstot[m].append(patient.getTotalPop())
    cured = 0
    for i in range(numTrials):
        if resultstot[delayedSteps + 149][i] <= 50:
            cured += 1
    print('freq cured = ' + str(100 * cured / float(numTrials)) + ' %')
    pylab.hist(resultstot[delayedSteps + 149], bins=20)
    pylab.xlabel('# viruses final')
    pylab.ylabel('# trials')
    pylab.title('numbers of viruses for delay = ' +
                str(delayedSteps) + ' and trials = ' + str(numTrials))


def simulationDelayedTreatment(delayedSteps, numTrials):
    """
    Runs simulations and make histograms for problem 1.

    Runs numTrials simulations to show the relationship between delayed
    treatment and patient outcome using a histogram.

    Histograms of final total virus populations are displayed for delays of 300,
    150, 75, 0 timesteps (followed by an additional 150 timesteps of
    simulation).

    numTrials: number of simulation runs to execute (an integer)
    """
    simulationWithDrugDelayed(100, 1000, 0.1, 0.05, {'guttagonol': False},
                              0.005, numTrials, delayedSteps)
    pylab.show()


def test1():
    """ Pb4 - A - 4 -1 """
    for i in range(0, 1000, 100):
        simulationWithDrugDelayed(i, 1000, 0.1, 0.05, {'guttagonol': False},
                                  0.005, 500, 150)


def test2():
    """ Pb4 - A - 4 - 2 """
    for i in range(100, 1000, 100):
        simulationWithDrugDelayed(100, i, 0.1, 0.05, {'guttagonol': False},
                                  0.005, 500, 150)


def test3():
    """ Pb4 - A - 4 - 3 """
    for i in range(10):
        simulationWithDrugDelayed(100, 1000, i / 20.0, 0.05,
                                  {'guttagonol': False}, 0.005, 500, 150)


def test4():
    """ Pb4 - A - 4 - 4 """
    for i in range(10):
        simulationWithDrugDelayed(100, 1000, 0.1, i / 20.0,
                                  {'guttagonol': False}, 0.005, 500, 150)


#
# PROBLEM 2
#
def simulationWithDrugs(numViruses, maxPop, maxBirthProb, clearProb,
                        resistances, mutProb, numTrials, delayedSteps):
    resultstot = [[] for k in range(delayedSteps + 300)]
    resultsres = [[] for k in range(delayedSteps + 300)]
    for l in range(numTrials):
        viruses = []
        for k in range(numViruses):
            viruses.append(ResistantVirus(maxBirthProb, clearProb,
                                          resistances, mutProb))
        patient = TreatedPatient(viruses, maxPop)
        for m in range(delayedSteps + 300):
            if m == 150:
                patient.addPrescription("guttagonol")
            if m == 150 + delayedSteps:
                patient.addPrescription("grimpex")
            patient.update()
            resultstot[m].append(patient.getTotalPop())
    for i in range(delayedSteps + 300):
        resultstot[i] = sum(resultstot[i]) / float(numTrials)
        resultsres[i] = sum(resultsres[i]) / float(numTrials)
    pylab.plot(resultstot, label="Total Virus population")
    pylab.plot(resultsres, label="Resistant Virus population")
    pylab.xlabel('time step')
    pylab.ylabel('# viruses')
    pylab.title('ResistantVirus simulation')
    pylab.legend()
    pylab.show()


def simulationWithDrugsDelayed(numViruses, maxPop, maxBirthProb, clearProb,
                               resistances, mutProb, numTrials, delayedSteps):
    resultstot = [[] for k in range(delayedSteps + 300)]
    for l in range(numTrials):
        viruses = []
        for k in range(numViruses):
            viruses.append(ResistantVirus(maxBirthProb, clearProb,
                                          resistances, mutProb))
        patient = TreatedPatient(viruses, maxPop)
        for m in range(delayedSteps+300):
            if m == 150:
                patient.addPrescription("guttagonol")
            if m == 150 + delayedSteps:
                patient.addPrescription("grimpex")
            patient.update()
            resultstot[m].append(patient.getTotalPop())

    cured = 0
    for i in range(numTrials):
        if resultstot[delayedSteps + 299][i] <= 50:
            cured += 1
    print('freq cured = ' + str(100 * cured / float(numTrials)) + ' %')
    pylab.hist(resultstot[delayedSteps + 299], bins=100)
    pylab.xlabel('# viruses final')
    pylab.ylabel('# trials')
    pylab.title('Nb of viruses for delay = ' + str(delayedSteps) +
                ' and trials = ' + str(numTrials) + '. Freq cured = ' +
                str(100 * cured / float(numTrials)) + ' % for 2 drugs\n' +
                ' mean = ' + str(pylab.mean(resultstot[delayedSteps + 299])) +
                ' and var = ' + str(pylab.var(resultstot[delayedSteps + 299])))


def simulationTwoDrugsDelayedTreatment(delayedSteps, numTrials):
    """
    Runs simulations and make histograms for problem 2.

    Runs numTrials simulations to show the relationship between administration
    of multiple drugs and patient outcome.

    Histograms of final total virus populations are displayed for lag times of
    300, 150, 75, 0 timesteps between adding drugs (followed by an additional
    150 timesteps of simulation).

    numTrials: number of simulation runs to execute (an integer)
    """
    simulationWithDrugsDelayed(100, 1000, 0.1, 0.05,
                               {'guttagonol': False, 'grimpex': False},
                               0.005, numTrials, delayedSteps)
    pylab.show()


def test6():
    """Problem 4 - B - 3"""
    for i in range(10):
        print(i / 1000.0)
        simulationWithDrugsDelayed(100, 1000, 0.1, 0.05,
                                   {'guttagonol': False, 'grimpex': False},
                                   i / 1000.0, 500, 150)


def simulationWithDrugsDelayed2(numViruses, maxPop, maxBirthProb, clearProb,
                                resistances, mutProb, numTrials, delayedSteps):
    resultstot = [[] for k in range(delayedSteps + 300)]
    for l in range(numTrials):
        viruses = []
        for k in range(numViruses):
            viruses.append(ResistantVirus(maxBirthProb, clearProb,
                                          resistances, mutProb))
        patient = TreatedPatient(viruses, maxPop)
        for m in range(delayedSteps + 300):
            if m == 150:
                patient.addPrescription("guttagonol")
            if m == 150+delayedSteps:
                patient.addPrescription("grimpex")
            patient.update()
            resultstot[m].append(patient.getTotalPop())
    cured = 0
    for i in range(numTrials):
        if resultstot[delayedSteps + 299][i] <= 50:
            cured += 1
    return 100 * cured / float(numTrials)


def test7():
    """Problem 4 - B - 4"""
    delayed = []
    result = []
    for i in range(0, 300, 25):
        delayed.append(i)
        result.append(simulationWithDrugsDelayed2(100, 1000, 0.1, 0.05,
                                                  {'guttagonol': False,
                                                   'grimpex': False},
                                                  0.005, 500, i))
    pylab.plot(delayed, result)
    pylab.xlabel('delayed Steps')
    pylab.ylabel('% cured')
    pylab.title('Relation between delay and number of cured patients')
    pylab.show()
