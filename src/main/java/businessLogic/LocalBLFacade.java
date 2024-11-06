package businessLogic;

import dataAccess.DataAccess;
import java.util.Date;
import java.util.List;
import domain.*;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class LocalBLFacade implements BLFacade {
    private final BLFacadeImplementation localFacade;

    public LocalBLFacade() {
    	DataAccess dataAccess = new DataAccess();
        this.localFacade = new BLFacadeImplementation(dataAccess);
    }

    @Override
    public List<String> getDepartCities() {
        return localFacade.getDepartCities();
    }

    @Override
    public List<String> getDestinationCities(String from) {
        return localFacade.getDestinationCities(from);
    }

    @Override
    public Ride createRide(List<String> info, Date date, int nPlaces, float price) throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
        return localFacade.createRide(info, date, nPlaces, price);
    }

    @Override
    public List<Ride> getRides(String from, String to, Date date) {
        return localFacade.getRides(from, to, date);
    }

    @Override
    public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
        return localFacade.getThisMonthDatesWithRides(from, to, date);
    }

    @Override
    public void initializeBD() {
        localFacade.initializeBD();
    }

    @Override
    public User getUser(String erab) {
        return localFacade.getUser(erab);
    }

    @Override
    public double getActualMoney(String erab) {
        return localFacade.getActualMoney(erab);
    }

    @Override
    public boolean isRegistered(String erab, String passwd) {
        return localFacade.isRegistered(erab, passwd);
    }

    @Override
    public Driver getDriver(String erab) {
        return localFacade.getDriver(erab);
    }

    @Override
    public Traveler getTraveler(String erab) {
        return localFacade.getTraveler(erab);
    }

    @Override
    public String getMotaByUsername(String erab) {
        return localFacade.getMotaByUsername(erab);
    }

    @Override
    public boolean addDriver(String username, String password) {
        return localFacade.addDriver(username, password);
    }

    @Override
    public boolean addTraveler(String username, String password) {
        return localFacade.addTraveler(username, password);
    }

    @Override
    public boolean gauzatuEragiketa(String username, double amount, boolean deposit) {
        return localFacade.gauzatuEragiketa(username, amount, deposit);
    }

    @Override
    public boolean bookRide(String username, Ride ride, int seats, double desk) {
        return localFacade.bookRide(username, ride, seats, desk);
    }

    @Override
    public List<Movement> getAllMovements(User user) {
        return localFacade.getAllMovements(user);
    }

    @Override
    public void addMovement(User user, String eragiketa, double amount) {
        localFacade.addMovement(user, eragiketa, amount);
    }

    @Override
    public List<Booking> getBookedRides(String username) {
        return localFacade.getBookedRides(username);
    }

    @Override
    public List<Booking> getPastBookedRides(String username) {
        return localFacade.getPastBookedRides(username);
    }

    @Override
    public void updateTraveler(Traveler traveler) {
        localFacade.updateTraveler(traveler);
    }

    @Override
    public void updateDriver(Driver driver) {
        localFacade.updateDriver(driver);
    }

    @Override
    public void updateUser(User user) {
        localFacade.updateUser(user);
    }

    @Override
    public void updateBooking(Booking booking) {
        localFacade.updateBooking(booking);
    }

    @Override
    public List<Booking> getBookingFromDriver(String username) {
        return localFacade.getBookingFromDriver(username);
    }

    @Override
    public List<Ride> getRidesByDriver(String username) {
        return localFacade.getRidesByDriver(username);
    }

    @Override
    public void cancelRide(Ride ride) {
        localFacade.cancelRide(ride);
    }

    @Override
    public boolean addCar(String username, Car kotxe) {
        return localFacade.addCar(username, kotxe);
    }

    @Override
    public boolean isAdded(String username, String matr) {
        return localFacade.isAdded(username, matr);
    }

    @Override
    public Car getKotxeByMatrikula(String matr) {
        return localFacade.getKotxeByMatrikula(matr);
    }

    @Override
    public boolean erreklamazioaBidali(String nor, String nori, Date gaur, Booking book, String textua, boolean aurk) {
        return localFacade.erreklamazioaBidali(nor, nori, gaur, book, textua, aurk);
    }

    @Override
    public void updateComplaint(Complaint erreklamazioa) {
        localFacade.updateComplaint(erreklamazioa);
    }

    @Override
    public void createDiscount(Discount di) {
        localFacade.createDiscount(di);
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return localFacade.getAllDiscounts();
    }

    @Override
    public void deleteDiscount(Discount dis) {
        localFacade.deleteDiscount(dis);
    }

    @Override
    public void updateDiscount(Discount dis) {
        localFacade.updateDiscount(dis);
    }

    @Override
    public Discount getDiscount(String desk) {
        return localFacade.getDiscount(desk);
    }

    @Override
    public void deleteCar(Car car) {
        localFacade.deleteCar(car);
    }

    @Override
    public List<User> getUserList() {
        return localFacade.getUserList();
    }

    @Override
    public void deleteUser(User us) {
        localFacade.deleteUser(us);
    }

    @Override
    public List<Alert> getAlertsByUsername(String username) {
        return localFacade.getAlertsByUsername(username);
    }

    @Override
    public Alert getAlert(int alertNumber) {
        return localFacade.getAlert(alertNumber);
    }

    @Override
    public void updateAlert(Alert alert) {
        localFacade.updateAlert(alert);
    }

    @Override
    public boolean updateAlertaAurkituak(String username) {
        return localFacade.updateAlertaAurkituak(username);
    }

    @Override
    public boolean createAlert(Alert alert) {
        return localFacade.createAlert(alert);
    }

    @Override
    public boolean deleteAlert(int alertNumber) {
        return localFacade.deleteAlert(alertNumber);
    }

    @Override
    public Complaint getComplaintsByBook(Booking book) {
        return localFacade.getComplaintsByBook(book);
    }

    @Override
    public ExtendedIterator<String> getDepartingCitiesIterator() {
        return localFacade.getDepartingCitiesIterator();
    }
}
