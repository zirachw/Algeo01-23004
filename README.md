# Rudal Sekeloa Calculator

1st Big Task IF2123 Aljabar Linear dan Geometri (Sistem Persamaan Linier, Determinan, dan Aplikasinya)

## Table of Contents

- [Group 10](#group-10)
- [General Information](#general-information)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Setup](#setup)
- [How-to-use](#how-to-use)
- [Structure](#structure)

## Group 10

1. Razi Rachman Widyadhana (13523004) - [@zirachw](https://github.com/zirachw)
2. Nayaka Ghana Subrata (13523090) - [@Nayekah](https://github.com/Nayekah)
3. Muhammad Adha Ridwan (13523098) - [@adharidwan](https://github.com/adharidwan)

## General Information

The main goal of this project is to create one or more linear algebra libraries in Java. The library contains functions such as
Gauss elimination, Gauss-Jordan elimination, determining the matrix flip, calculating the determinant, Cramer's rule (Cramer's rule specifically for SPL with n variables and n equations).
In addition, the library is used in Java programs to solve various problems modeled in the form of SPL, solve interpolation problems, and regression problems.

## Technologies Used

- Java

## Features

- Solve systems of linear equations through the application of the Gauss Elimination Method, Gauss-Jordan Elimination Method, Inverse Method, and Cramer's Rule.
- Determine the determinant by using the Cofactor and Inverse matrix techniques.
- Calculate the inverse of a matrix using the Gauss elimination method and the Adjoint matrix method.
- Solve problems that require polynomial interpolation, bicubic interpolation, and multiple regression approaches.

## Setup

- Download and Install [Java Virtual Environment](https://www.java.com/en/download/)
- Download dan Install [Java Development Kit](https://www.oracle.com/java/technologies/downloads/)
- Download all folder and files on this repository or simply clone this repo!

## How-to-use

    git clone https://github.com/zirachw/Algeo01-23004
    cd Algeo01-23004/src
    java -cp ../bin Main

## Structure

```
├── bin          # contains Java bytecode (*.class)
│   └── ...
│
├── doc          # contains documents
│   └── ...
│
├── src          # contains Java source codes
│   └── ...
│
├── test         # contains test cases
│   │
│   ├── case     # contains given cases
│   │    └── ...
│   │
│   ├── img      # contains source images for ImageResizing
│   │    └── ...
│   │
│   └── result   # contains results of calculator
│        └── ...
│   
└── README.md    # brief explanation of the program
```
