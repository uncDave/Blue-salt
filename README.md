# Blue-salt
A drone Management System

# Drone Management System

## Overview
The Drone Management System is a platform that allows administrators to manage drones and their medications. The system enables administrators to perform the following tasks:
- Create and update drones and medications.
- Load medications onto drones while ensuring they do not exceed the drone's weight capacity.
- Remove medications from drones.
- View all drones and medications.
- Monitor and log the battery percentage of drones through a scheduled task.

This system is built with Spring Boot and uses PostgreSQL for database storage and cloudinary for image storage.

## Features
- **Drone Management**: Admins can create, view, update, and delete drones.
- **Medication Management**: Admins can create, view, update, and delete medications.
- **Loading and Unloading**: Admins can load medications onto drones, ensuring that the total weight of the loaded medications does not exceed the drone's weight capacity. Admins can also remove medications from drones.
- **Battery Monitoring**: A scheduled task runs periodically to check the battery percentage of each drone and logs the data for auditing purposes.

## Tech Stack
- **Backend**: Spring Boot
- **Database**: PostgreSQL
- **Authentication**: Role-based authorization (Admin)

## Getting Started

### Prerequisites
1. **Java 17+**: Ensure that you have JDK 17 or later installed on your machine.
2. **PostgreSQL**: A running PostgreSQL database. You can either set up PostgreSQL locally or use a cloud-based instance.
3. **Maven**: Apache Maven for building the project.

### Setting Up the Database

1. **Install PostgreSQL**: If you don’t have PostgreSQL installed, download and install it from [PostgreSQL Downloads](https://www.postgresql.org/download/).
2. **Create a Database**: Create a new PostgreSQL database for the project.
   ```bash
   CREATE DATABASE drone_management;


### Commit history
git log

commit 8451384dce3b2cf4b28776ecb6213c364ad7b35d (HEAD -> main, origin/main)
Author: C.David 
Date:   Wed Jan 22 17:02:01 2025 +0100

    unloading drone ft

commit 94dc3af24e64e6db94b95545df49c0ec6f23965f
Merge: a93b867 02b4443
Author: C.David 
Date:   Wed Jan 22 12:56:30 2025 +0100



