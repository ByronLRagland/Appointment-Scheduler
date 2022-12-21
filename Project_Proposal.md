# Capstone Proposal
 
## 1. Problem Statement
 
Define a problem someone is having that can be solved with software:
 
Many small businesses still use physical log books or simple digital records to keep track of their customer appointments. This method makes it hard to keep track of the timeline, and customers would either have to physically book an appointment or call in. It is also difficult for returning customers to look for a specific employee that they like from the business since they would have difficulty figuring out said employee's work schedule without directly calling.
 
Without an organized system, the employees could easily miss an appointment or fail to notice a mistake in the appointment (timing or otherwise).
 
 
 
> Using a barber shop that lacks a scheduling system as an example,
>
 
> If a customer would like to book a haircut, they would have to call in, ask for which barber is available, obtain a price for the service. The admin would have to note this down, inform the employee of the new work assignment. Ignoring the laborious process, there are many things that could easily go wrong because the information needs to be relayed across multiple roles while multiple instances of information could be present.
 
>
> This process becomes even more complicated should the customer decide to make a change to their appointment. Not only would they have to call in again, inform admin of the change, obtain a new available time block; on the admins end, they would have to communicate with the employees again for the next available time, remove the previous appointment record, note down the new one while expecting the employee to be able to memorize the change. Once again, such a process is not only tedious but also extremely prone to errors.
 
>
 
 
## 2. Technical Solution
 
Briefly describe a technical solution to your problem with a couple concrete scenarios.
 
### Example
 
> Create an application that allows customers to directly book an appointment with employees who mark out time blocks of when they would be available. All parties involved would be able to view and delete their appointments and the changes made would be immediately available for all to see. The admin, under this system, would be able to better clarify what types of services are provided and their pricing and have a better overview of their available staff.
>
> ### Scenario 1
> Jason is a new customer, he wants to get a haircut on Sunday afternoon. He uses the scheduler to see all of the barbers available during Sunday afternoon. Jason chooses Jack to do his haircut because he's able to see that Jack is specialized in this particular haircut he wants. Unfortunately, Jason later learned that he had to run an errand during his appointment with Jack later. Jason is able to view his appointment at any time using his customer account and cancels it directly.
>
 
> ### Scenario 2
> Amy is a returning customer, she wants to get a hair dye from Alice. Amy is able to go view all of the time blocks that Alice is available. Choose a time block long enough for a hair dye.
 
> ### Scenario 3
> Alice is an employee. She works at the barber shop as a part time employee. Her current schedule lets her work Monday, Wednesday, and Friday from 5pm to 8pm. Alice uses her employee account to mark those time blocks as available, and is able to see if a customer chooses her for an appointment.
 
 
## 3. Glossary
 
Define key domain terms. This won't map one-to-one with model classes, but it may be close.
 
### Employee
- The employee who will note down the time block where they are available by creating appointments. The employee provides a service to the customer. Type of service and when it will take place will be recorded in appointment.
 
### Appointment
- A record to keep track of a future event that includes: the customer & employee, time to take place, when it should end, type of service to be provided. An employee is able to create an appointment with their own id and the start & end time.
 
### Customer
- The customer who will complete the appointment with their customer id and the service they want, after viewing all of the appointments available provided by the employee.
 
### Service
- Contains a price, duration, name and description.
 
### Admin
 
- An AppUser of Admin type is able to add, delete, and update a service. An admit is able to view all, add, update and delete employees.
 
 
## 4. High Level Requirement
 
Briefly describe what each user role/authority can do. (These are user stories.)
 
### Example
 
> - Sign up to become a customer (Anyone)
> - Create an account (Customer)
> - Sign in to their account (Customer, Employee, Admin)
> - Create an appointment (Employee)
> - Delete an unbooked appointment (Employee)
> - Book an appointment (Customer)
> - Delete a booked appointment (Customer)
> - Add a new employee (Admin)
> - Add a new service (Admin)
 
 
## 5. User Stories/Scenarios
 
Elaborate use stories.
 
### Example
 
> ### Create an Appointment
>
> Create an appointment that the customer can book.
>
> Suggested data:
> - Start time
> - End time
>
> **Precondition**: User must be logged in with the Employee role.
>
> **Post-condition**: An appointment with only the employee id, start and end time is created.
>
> ### Book an Appointment
>
> A customer books an appointment from the list of available appointments.
>
> Suggested data:
> - Choose a service
>
> **Precondition**: User must be logged in with the customer role.
>
> **Post-condition**: An appointment will be booked by the customer, making it unavailable on the list for all customers to see. The customer who booked the appointment and the employee attending to the appointment will be able to see this appointment in their "Your appointments" tab.
>
 
> ### Cancel an Appointment
>
> A customer cancels a booked appointment.
>
>
> **Precondition**: User must be logged in with the customer role.
>
> **Post-condition**: The previously booked appointment will now once again be made available for all customers to see. It will be removed from the "Your appointments” table for the customer & the employee involved. Its service and customer id will be removed.
>
 
> ### Create a customer account
>
> A user needs to sign up in order to book an appointment.
> Suggested data:
> - email address (unique)
> - name
> - phone number
> - password
>
> **Precondition**: Any user who is not signed in will be able to see the sign in button on the navbar. After being redirected to the sign in page, they will have the option to sign up for an account.
>
> **Post-condition**: A customer account is created. The customer should now be able to log in using their email and password.
 
> ### Create a service
>
> An admin adds a new service to their business. They may click on the "view services" button on their navbar and view all of the current services available. Options will be provided to add, delete & edit a service.
> Suggested data:
> - duration
> - price
> - service name
> - service description
>
> **Precondition**: The user must be logged in as an Admin.
>
> **Post-condition**: A new service will be added for a customer to choose from.
>
>
 

