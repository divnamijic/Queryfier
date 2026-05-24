package edu.rit.croatia.iste422.g6.qf.io.parser.util;

// Package imports
import edu.rit.croatia.iste422.g6.qf.model.db.Attribute;
import edu.rit.croatia.iste422.g6.qf.model.db.AttributeDataType;
import edu.rit.croatia.iste422.g6.qf.model.db.DatabaseSchema;
import edu.rit.croatia.iste422.g6.qf.model.db.Entity;
import edu.rit.croatia.iste422.g6.qf.model.db.EntityRelationship;

// Java imports
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The DatabaseSchemaGenerator class provides methods for generating random
 * instances of {@link DatabaseSchema} representing various types of databases.
 * <p>
 * This class offers a mechanism to create sample database schemas for testing
 * or demonstration purposes.
 * 
 * @author Swen Grgicevic
 * @author Doroteja Krtalic
 * @author Michel Brassard
 * @author Petra Cesar
 * @author Divna Mijic
 */
// The class has been created by "The Sixth Sense" Group.
public class DatabaseSchemaGenerator {

    private static final Random rand = new SecureRandom();

    /**
     * Generates a random DatabaseSchema representing a database of a predefined
     * type.
     *
     * @return A randomly generated {@link DatabaseSchema} object.
     */
    public static DatabaseSchema generateRandomDatabase() {
        String[] methods = { "generateSchoolDatabase", "generateTableTopDatabase", "generateLibraryDatabase",
                "generatePublicTransportDatabase" };
        try {
            String methodName = methods[rand.nextInt(methods.length)];
            return (DatabaseSchema) DatabaseSchemaGenerator.class
                    .getMethod(methodName).invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
            // Since there is a fallback, no need to catch these
        }

        // Fallback
        return DatabaseSchemaGenerator.generateSchoolDatabase();
    }

    /**
     * Generates a random DatabaseSchema representing a school database.
     *
     * @return A randomly generated {@link DatabaseSchema} object for a school
     *         database.
     */
    public static DatabaseSchema generateSchoolDatabase() {
        final List<Entity> entities = new ArrayList<>();

        Entity instructor = new Entity("Instructor");

        Attribute inId = new Attribute("ID", AttributeDataType.VARCHAR);
        Attribute inFName = new Attribute("First Name", AttributeDataType.VARCHAR);
        Attribute inLName = new Attribute("Last Name", AttributeDataType.VARCHAR);

        instructor.addAttribute(inId);
        instructor.addAttribute(inFName);
        instructor.addAttribute(inLName);

        Entity student = new Entity("Student");
        Attribute stId = new Attribute("ID", AttributeDataType.VARCHAR);
        Attribute stFName = new Attribute("First Name", AttributeDataType.VARCHAR);
        Attribute stLName = new Attribute("Last Name", AttributeDataType.VARCHAR);

        student.addAttribute(stId);
        student.addAttribute(stFName);
        student.addAttribute(stLName);

        Entity course = new Entity("Course");
        Attribute courseId = new Attribute("ID", AttributeDataType.VARCHAR);
        Attribute courseName = new Attribute("Name", AttributeDataType.VARCHAR);

        course.addAttribute(courseId);
        course.addAttribute(courseName);

        Entity department = new Entity("Department");
        Attribute deptId = new Attribute("ID", AttributeDataType.VARCHAR);
        Attribute deptName = new Attribute("Name", AttributeDataType.VARCHAR);

        department.addAttribute(deptId);
        department.addAttribute(deptName);

        Entity classroom = new Entity("Classroom");
        Attribute roomNumber = new Attribute("Room Number", AttributeDataType.INT);

        classroom.addAttribute(roomNumber);

        entities.add(instructor);
        entities.add(student);
        entities.add(course);
        entities.add(department);

        final List<EntityRelationship> entityRelationships = new ArrayList<>();
        entityRelationships.add(new EntityRelationship(instructor, inId, course, courseId));
        entityRelationships.add(new EntityRelationship(student, stId, course, courseId));
        entityRelationships.add(new EntityRelationship(instructor, inId, department, deptId));
        entityRelationships.add(new EntityRelationship(student, stId, department, deptId));
        entityRelationships.add(new EntityRelationship(course, courseId, department, deptId));
        entityRelationships.add(new EntityRelationship(course, courseId, classroom, roomNumber));

        return new DatabaseSchema(entities, entityRelationships);
    }

    /**
     * Generates a random DatabaseSchema representing a school database.
     *
     * @return A randomly generated {@link DatabaseSchema} object for a table top
     *         database.
     */
    public static DatabaseSchema generateTableTopDatabase() {
        final List<Entity> entities = new ArrayList<>();

        Entity event = new Entity("Event");
        Attribute eventId = new Attribute("Event ID", AttributeDataType.INT);
        Attribute eventName = new Attribute("Event Name", AttributeDataType.VARCHAR);
        Attribute eventDate = new Attribute("Event Date", AttributeDataType.DATE);
        Attribute eventLocation = new Attribute("Event Location", AttributeDataType.VARCHAR);
        Attribute eventDescription = new Attribute("Event Description", AttributeDataType.VARCHAR);

        event.addAttribute(eventId);
        event.addAttribute(eventName);
        event.addAttribute(eventDate);
        event.addAttribute(eventLocation);
        event.addAttribute(eventDescription);

        Entity attendee = new Entity("Attendee");
        Attribute attendeeId = new Attribute("Attendee ID", AttributeDataType.INT);
        Attribute attendeeName = new Attribute("Attendee Name", AttributeDataType.VARCHAR);
        Attribute attendeeEmail = new Attribute("Attendee Email", AttributeDataType.VARCHAR);
        Attribute attendeePhone = new Attribute("Attendee Phone", AttributeDataType.VARCHAR);

        attendee.addAttribute(attendeeId);
        attendee.addAttribute(attendeeName);
        attendee.addAttribute(attendeeEmail);
        attendee.addAttribute(attendeePhone);

        Entity gameSession = new Entity("Game Session");
        Attribute sessionId = new Attribute("Session ID", AttributeDataType.INT);
        Attribute sessionTime = new Attribute("Session Time", AttributeDataType.TIME);
        Attribute sessionDuration = new Attribute("Session Duration", AttributeDataType.INT);
        Attribute sessionGame = new Attribute("Session Game", AttributeDataType.VARCHAR);

        gameSession.addAttribute(sessionId);
        gameSession.addAttribute(sessionTime);
        gameSession.addAttribute(sessionDuration);
        gameSession.addAttribute(sessionGame);

        Entity gameMaster = new Entity("Game Master");
        Attribute gmId = new Attribute("Game Master ID", AttributeDataType.INT);
        Attribute gmName = new Attribute("Game Master Name", AttributeDataType.VARCHAR);
        Attribute gmExperience = new Attribute("Game Master Experience", AttributeDataType.VARCHAR);

        gameMaster.addAttribute(gmId);
        gameMaster.addAttribute(gmName);
        gameMaster.addAttribute(gmExperience);

        Entity gameType = new Entity("Game Type");
        Attribute gameTypeId = new Attribute("Game Type ID", AttributeDataType.INT);
        Attribute gameTypeName = new Attribute("Game Type Name", AttributeDataType.VARCHAR);

        gameType.addAttribute(gameTypeId);
        gameType.addAttribute(gameTypeName);

        entities.add(event);
        entities.add(attendee);
        entities.add(gameSession);
        entities.add(gameMaster);
        entities.add(gameType);

        final List<EntityRelationship> entityRelationships = new ArrayList<>();
        entityRelationships.add(new EntityRelationship(event, eventId, attendee, attendeeId));
        entityRelationships.add(new EntityRelationship(event, eventId, gameSession, sessionId));
        entityRelationships.add(new EntityRelationship(gameSession, sessionGame, gameMaster, gmId));
        entityRelationships.add(new EntityRelationship(gameSession, sessionGame, gameType, gameTypeId));

        return new DatabaseSchema(entities, entityRelationships);

    }

    /**
     * Generates a random DatabaseSchema representing a school database.
     *
     * @return A randomly generated {@link DatabaseSchema} object for a library
     *         database.
     */
    public static DatabaseSchema generateLibraryDatabase() {
        final List<Entity> entities = new ArrayList<>();

        Entity book = new Entity("Book");
        Attribute bookId = new Attribute("Book ID", AttributeDataType.INT);
        Attribute bookTitle = new Attribute("Title", AttributeDataType.VARCHAR);
        Attribute bookAuthorId = new Attribute("Author ID", AttributeDataType.INT);
        Attribute bookGenre = new Attribute("Genre", AttributeDataType.VARCHAR);
        Attribute bookPublicationYear = new Attribute("Publication Year", AttributeDataType.INT);

        book.addAttribute(bookId);
        book.addAttribute(bookTitle);
        book.addAttribute(bookAuthorId);
        book.addAttribute(bookGenre);
        book.addAttribute(bookPublicationYear);

        Entity author = new Entity("Author");
        Attribute authorId = new Attribute("Author ID", AttributeDataType.INT);
        Attribute authorName = new Attribute("Author Name", AttributeDataType.VARCHAR);
        Attribute authorBirthYear = new Attribute("Birth Year", AttributeDataType.INT);

        author.addAttribute(authorId);
        author.addAttribute(authorName);
        author.addAttribute(authorBirthYear);

        Entity member = new Entity("Library Member");
        Attribute memberId = new Attribute("Member ID", AttributeDataType.INT);
        Attribute memberName = new Attribute("Member Name", AttributeDataType.VARCHAR);
        Attribute memberEmail = new Attribute("Email", AttributeDataType.VARCHAR);
        Attribute memberPhoneNumber = new Attribute("Phone Number", AttributeDataType.VARCHAR);

        member.addAttribute(memberId);
        member.addAttribute(memberName);
        member.addAttribute(memberEmail);
        member.addAttribute(memberPhoneNumber);

        Entity transaction = new Entity("Library Transaction");
        Attribute transactionId = new Attribute("Transaction ID", AttributeDataType.INT);
        Attribute transactionDate = new Attribute("Transaction Date", AttributeDataType.DATE);
        Attribute transactionBookId = new Attribute("Book ID", AttributeDataType.INT);
        Attribute transactionMemberId = new Attribute("Member ID", AttributeDataType.INT);

        transaction.addAttribute(transactionId);
        transaction.addAttribute(transactionDate);
        transaction.addAttribute(transactionBookId);
        transaction.addAttribute(transactionMemberId);

        entities.add(book);
        entities.add(author);
        entities.add(member);
        entities.add(transaction);

        final List<EntityRelationship> entityRelationships = new ArrayList<>();
        entityRelationships.add(new EntityRelationship(book, bookAuthorId, author, authorId));
        entityRelationships.add(new EntityRelationship(transaction, transactionBookId, book, bookId));
        entityRelationships.add(new EntityRelationship(transaction, transactionMemberId, member, memberId));

        return new DatabaseSchema(entities, entityRelationships);
    }

    /**
     * Generates a random DatabaseSchema representing a school database.
     *
     * @return A randomly generated {@link DatabaseSchema} object for a public
     *         transport database.
     */
    public static DatabaseSchema generatePublicTransportDatabase() {
        final List<Entity> entities = new ArrayList<>();

        Entity route = new Entity("Route");
        Attribute routeId = new Attribute("Route ID", AttributeDataType.INT);
        Attribute routeName = new Attribute("Route Name", AttributeDataType.VARCHAR);
        Attribute routeType = new Attribute("Route Type", AttributeDataType.VARCHAR);
        Attribute routeDistance = new Attribute("Route Distance (km)", AttributeDataType.FLOAT);
        Attribute routeDuration = new Attribute("Route Duration (min)", AttributeDataType.SMALLINT);
        Attribute routeStartLocation = new Attribute("Start Location", AttributeDataType.VARCHAR);
        Attribute routeEndLocation = new Attribute("End Location", AttributeDataType.VARCHAR);

        route.addAttribute(routeId);
        route.addAttribute(routeName);
        route.addAttribute(routeType);
        route.addAttribute(routeDistance);
        route.addAttribute(routeDuration);
        route.addAttribute(routeStartLocation);
        route.addAttribute(routeEndLocation);

        Entity vehicle = new Entity("Vehicle");
        Attribute vehicleId = new Attribute("Vehicle ID", AttributeDataType.INT);
        Attribute vehicleType = new Attribute("Vehicle Type", AttributeDataType.VARCHAR);
        Attribute vehicleCapacity = new Attribute("Capacity", AttributeDataType.SMALLINT);
        Attribute vehicleManufacturer = new Attribute("Manufacturer", AttributeDataType.VARCHAR);
        Attribute vehicleYear = new Attribute("Manufacture Year", AttributeDataType.SMALLINT);
        Attribute vehicleRegistration = new Attribute("Registration Number", AttributeDataType.VARCHAR);

        vehicle.addAttribute(vehicleId);
        vehicle.addAttribute(vehicleType);
        vehicle.addAttribute(vehicleCapacity);
        vehicle.addAttribute(vehicleManufacturer);
        vehicle.addAttribute(vehicleYear);
        vehicle.addAttribute(vehicleRegistration);

        Entity passenger = new Entity("Passenger");
        Attribute passengerId = new Attribute("Passenger ID", AttributeDataType.INT);
        Attribute passengerName = new Attribute("Name", AttributeDataType.VARCHAR);
        Attribute passengerAge = new Attribute("Age", AttributeDataType.TINYINT);
        Attribute passengerGender = new Attribute("Gender", AttributeDataType.VARCHAR);
        Attribute passengerTicketType = new Attribute("Ticket Type", AttributeDataType.VARCHAR);

        passenger.addAttribute(passengerId);
        passenger.addAttribute(passengerName);
        passenger.addAttribute(passengerAge);
        passenger.addAttribute(passengerGender);
        passenger.addAttribute(passengerTicketType);

        Entity ticket = new Entity("Ticket");
        Attribute ticketId = new Attribute("Ticket ID", AttributeDataType.INT);
        Attribute ticketPrice = new Attribute("Price (USD)", AttributeDataType.DECIMAL);
        Attribute ticketPurchaseDate = new Attribute("Purchase Date", AttributeDataType.DATE);

        ticket.addAttribute(ticketId);
        ticket.addAttribute(ticketPrice);
        ticket.addAttribute(ticketPurchaseDate);

        entities.add(route);
        entities.add(vehicle);
        entities.add(passenger);
        entities.add(ticket);

        final List<EntityRelationship> entityRelationships = new ArrayList<>();
        entityRelationships.add(new EntityRelationship(route, routeId, vehicle, vehicleId));
        entityRelationships.add(new EntityRelationship(passenger, passengerId, ticket, ticketId));

        return new DatabaseSchema(entities, entityRelationships);
    }

    /**
     * The private constructor for the DatabaseSchemaGenerator class.
     * <p>
     * This constructor is private to prevent the instantiation of the class, as the
     * class is intended to be used as a utility class with static methods for
     * generating random database schemas.
     */
    private DatabaseSchemaGenerator() {
    }

}
