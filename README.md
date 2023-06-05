# Streamlining-Decryption-Outsourcing-and-Key-Generation-in-Inner-Product-Functional-Encryption
This is an implementation of Inner Product Functional Encryption with Outsourced Decryption and Key Generation.

This implementation is a research prototype built for micro-benchmarking purposes, and is not intended to be used in production-level code as it has not been carefully analyzed for potential security flaws.

## Prerequisites
Make sure you have the following installed:
> The Java Pairing-Based Cryptography Library ([JPBC](http://http://gas.dia.unisa.it/projects/jpbc))

## Installation
1. Download jpbc_2_0_0.zip (or jpbc_2_0_0.tar.gz depending on your OS).
2. Unzip it and locate the jar directory (jpbc-2.0.0\jars).
3. Add these external jar files in the directory into your Java build path.
4. Locate the curves directory (jpbc-2.0.0\params\curves).
5. Add curve files to use for pairings in your project directory.

## Running a Test
Run Main.java in which you specify the dimension of vectors (line 18). Then, two vectors (lines 43 and 57) are encrypted and tested.
