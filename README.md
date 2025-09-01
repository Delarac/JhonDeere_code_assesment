Solution based on the Challenge: 

Company Use
Coding Challenge: Backend Developer Position
The goal of this challenge is to assess your coding skills, problem-solving abilities, and
understanding of software architecture. Below are the details of the task you will need to
complete:
Scenario & Context
Machines send data to our backend deployed in cloud environment. Each time a machine
starts sending data, a sessionGuid is created and attached to the messages going forward
Sample message 1:
{
sessionGuid: "a65de8c4-6385-4008-be36-5df0c5104fd5",
sequenceNumber: 1,
machineId: 1,
data: [
{
type: "distance",
unit: "m",
value: "100"
},
{
type: "workedSurface",
unit: "m2",
value: "600"
}
]
}
Sample message 2:
{
sessionGuid: "a65de8c4-6385-4008-be36-5df0c5104fd5",
sequenceNumber: 2,
machineId: 1,
data: [
{
type: "distance",
unit: "m",
value: "102"
},
{
type: "workedSurface",
unit: "m2",
value: "610"
}
]
}
We receive the data into a messaging application (receiving queue “sqs1”). The service we
ask you to create reads the message, processed it, and forwards it to the next messaging
queue (“sqs2”).
The new service processes the machine data from the queue and proceeds to call an
external service to check if the machine is authorized to send data. For simplification, we
assume that the decision is based on the unique machine ID that is assigned to each
physical machine. The authorized machine IDs are maintained in a whitelist.
Company Use
The message should only be forward if it is successfully authorized (service can be
mocked for simplification) and the sequence number for the unique message and session
pair was not already processed (to be tracked& persisted in an database)
Task summary
Keeping the provided scenario in mind, here is a quick overview of what we would like to be
included in your project:
1. Messaging Service Integration:
o Implement a solution that reads messages from a messaging service (e.g., AWS
SQS).
2. HTTP Request Handling:
o Make an HTTP request to a microservice based on the provided API definition.
o Utilize an appropriate messaging client for this interaction.
3. Database Interaction:
o Process the response and save the result in a database.
4. Message Forwarding:
o Forward the original message to the next step in the workflow.
5. Testing:
o Write at least one integration test to verify the overall functionality.
Write at least one unit test for a service class in your implementation.
Please consider the following requirements concerning the deliverable
• Containerization: Package your application using a container solution (e.g., Docker or
Podman).
• Version Control: Use Git for your project and provide a repository link with a clear
commit history. If Git is not feasible, you may submit a zip file of your project.
• Instructions: Include clear instructions on how to run your application..
Evaluation Criteria
Your submission will be evaluated based on the following criteria:
• Completeness and correctness of the implementation
• Robustness & Resilience of the code
• Code quality and maintainability
• Quality of documentation
• Effectiveness and coverage of tests
We look forward to seeing your approach to this challenge and how you can contribute to our
team. If you have any questions or need further clarification, please don’t hesitate to reach out.
Good luck, and we can’t wait to see what you create!
