package com.example.capstone2_adventureandoutdoorexperience.Service;

import com.example.capstone2_adventureandoutdoorexperience.API.ApiException;
import com.example.capstone2_adventureandoutdoorexperience.Model.Adventure;
import com.example.capstone2_adventureandoutdoorexperience.Model.AdventureRequest;
import com.example.capstone2_adventureandoutdoorexperience.Model.User;
import com.example.capstone2_adventureandoutdoorexperience.Repository.AdventureRepository;
import com.example.capstone2_adventureandoutdoorexperience.Repository.AdventureRequestRepository;
import com.example.capstone2_adventureandoutdoorexperience.Repository.UserGearRepository;
import com.example.capstone2_adventureandoutdoorexperience.Repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdventureRequestService {





        private final AdventureRequestRepository adventureRequestRepository;
        private final UserRepository userRepository;
        private final AdventureRepository adventureRepository;
        private final UserGearRepository userGearRepository;

        public List<AdventureRequest> getAllAdventureRequest() {
            return adventureRequestRepository.findAll();
        }

        public List<AdventureRequest> getByAdventure(Integer adventureId) {
            return adventureRequestRepository.findAdventureRequestByAdventureId(adventureId);
        }

        public List<AdventureRequest> getByUser(Integer userId) {
            return adventureRequestRepository.findAdventureRequestByUserId(userId);
        }



    public void addAdventureRequest(AdventureRequest req) {

        if (req.getUserId() == null)
            throw new ApiException("User ID is required");

        if (req.getAdventureId() == null)

            throw new ApiException("Adventure ID is required");
        if (req.getUserGearId() == null)

            throw new ApiException("Gear ID is required");


        User user = userRepository.findUserById(req.getUserId());

        if (user == null)
            throw new ApiException("User not found");

        Adventure adv = adventureRepository.findAdventureById(req.getAdventureId());

        if (adv == null)
            throw new ApiException("Adventure not found");

        if (!userGearRepository.existsByIdAndUserId(req.getUserGearId(), req.getUserId()))
            throw new ApiException("Gear not owned by user");

        if (adv.getMinAge() != null) {

            int age = java.time.Period.between(user.getDateOfBirth(), java.time.LocalDate.now()).getYears();

            if (age < adv.getMinAge())
                throw new ApiException("Minimum age for this adventure is " + adv.getMinAge());
        }

        if (adv.getGenderRestriction() != null && !"any".equalsIgnoreCase(adv.getGenderRestriction())) {
            if (user.getGender() == null || !adv.getGenderRestriction().equalsIgnoreCase(user.getGender()))
                throw new ApiException("This adventure is restricted to " + adv.getGenderRestriction());
        }

        if (adv.getCreatorId().equals(req.getUserId()))
            throw new ApiException("Creator cannot request to join their own adventure");

        List<AdventureRequest> existing = adventureRequestRepository.findByUserIdAndAdventureId(req.getUserId(), req.getAdventureId());
        for (AdventureRequest ar : existing) {

            if (!"REJECTED".equalsIgnoreCase(ar.getStatus()))

                throw new ApiException("You already have an active request for this adventure");
        }

        req.setStatus("PENDING");

        if (!adv.getRequireApproval()) {


            int active = countActiveParticipants(adv.getId());


            if (active >= adv.getTotalNeeded()) throw new ApiException("Adventure is full");
            req.setStatus("APPROVED");
        }

        if (req.getCreatedAt() == null) req.setCreatedAt(java.time.LocalDateTime.now());

        adventureRequestRepository.save(req);
    }







    public void addAdventureRequests(List<AdventureRequest> requests) {
        if (requests == null || requests.isEmpty()) return;

        int i = 0;
        for (AdventureRequest r : requests) {
            i++;

            if (r.getUserId() == null) throw new ApiException("Item #" + i + ": User ID is required");
            if (r.getAdventureId() == null) throw new ApiException("Item #" + i + ": Adventure ID is required");
            if (r.getUserGearId() == null) throw new ApiException("Item #" + i + ": Gear ID is required");

            User user = userRepository.findUserById(r.getUserId());
            if (user == null) throw new ApiException("Item #" + i + ": User not found");

            Adventure adv = adventureRepository.findAdventureById(r.getAdventureId());
            if (adv == null) throw new ApiException("Item #" + i + ": Adventure not found");

            if (!userGearRepository.existsByIdAndUserId(r.getUserGearId(), r.getUserId()))
                throw new ApiException("Item #" + i + ": Gear not owned by user");

            if (adv.getMinAge() != null) {
                int age = java.time.Period.between(user.getDateOfBirth(), java.time.LocalDate.now()).getYears();
                if (age < adv.getMinAge())
                    throw new ApiException("Item #" + i + ": Minimum age for this adventure is " + adv.getMinAge());
            }

            if (adv.getGenderRestriction() != null && !"any".equalsIgnoreCase(adv.getGenderRestriction())) {
                if (user.getGender() == null || !adv.getGenderRestriction().equalsIgnoreCase(user.getGender()))
                    throw new ApiException("Item #" + i + ": This adventure is restricted to " + adv.getGenderRestriction());
            }

            if (adv.getCreatorId().equals(r.getUserId()))
                throw new ApiException("Item #" + i + ": Creator cannot request to join their own adventure");

            List<AdventureRequest> existing = adventureRequestRepository.findByUserIdAndAdventureId(r.getUserId(), r.getAdventureId());
            for (AdventureRequest ar : existing) {
                if (!"REJECTED".equalsIgnoreCase(ar.getStatus()))
                    throw new ApiException("Item #" + i + ": Active request already exists");
            }

            r.setStatus("PENDING");

            if (!adv.getRequireApproval()) {
                int active = countActiveParticipants(adv.getId());
                if (active >= adv.getTotalNeeded())
                    throw new ApiException("Item #" + i + ": Adventure is full");
                r.setStatus("APPROVED");
            }

            if (r.getCreatedAt() == null) r.setCreatedAt(java.time.LocalDateTime.now());
        }

        adventureRequestRepository.saveAll(requests);
    }










        public void updateAdventureRequest(Integer id, AdventureRequest newReq) {
            AdventureRequest old = adventureRequestRepository.findAdventureRequestById(id);
            if (old == null) throw new ApiException("AdventureRequest not found");

            if (newReq.getUserId() == null) throw new ApiException("User ID is required");
            if (newReq.getAdventureId() == null) throw new ApiException("Adventure ID is required");
            if (newReq.getUserGearId() == null) throw new ApiException("Gear ID is required");
            if (newReq.getStatus() == null || !newReq.getStatus().matches("(?i)^(paid|approved|attended|released|rejected)$"))
                throw new ApiException("Invalid status");

            User user = userRepository.findUserById(newReq.getUserId());
            if (user == null) throw new ApiException("User not found");

            Adventure adv = adventureRepository.findAdventureById(newReq.getAdventureId());
            if (adv == null) throw new ApiException("Adventure not found");

            if (!userGearRepository.existsByIdAndUserId(newReq.getUserGearId(), newReq.getUserId()))
                throw new ApiException("Gear not owned by user");

            old.setUserId(newReq.getUserId());
            old.setAdventureId(newReq.getAdventureId());
            old.setUserGearId(newReq.getUserGearId());
            old.setStatus(newReq.getStatus());

            adventureRequestRepository.save(old);
        }

        public void deleteAdventureRequest(Integer id) {
            AdventureRequest req = adventureRequestRepository.findAdventureRequestById(id);
            if (req == null) throw new ApiException("AdventureRequest not found");
            adventureRequestRepository.delete(req);
        }

        public List<AdventureRequest> getRequestsForAdventure(Integer adventureId, Integer currentUserId) {
            Adventure adventure = adventureRepository.findAdventureById(adventureId);
            if (adventure == null) throw new ApiException("Adventure not found");
            if (!adventure.getCreatorId().equals(currentUserId))
                throw new ApiException("You are not allowed to see requests for this adventure");
            return adventureRequestRepository.findAdventureRequestByAdventureId(adventureId);
        }






    public void approveRequest(Integer userId, Integer requestId) {
        AdventureRequest req = adventureRequestRepository.findAdventureRequestById(requestId);
        if (req == null) throw new ApiException("AdventureRequest not found");

        Adventure adv = adventureRepository.findAdventureById(req.getAdventureId());
        if (adv == null) throw new ApiException("Adventure not found");
        if (!adv.getCreatorId().equals(userId)) throw new ApiException("Only the adventure creator can approve this request");

        if ("REJECTED".equalsIgnoreCase(req.getStatus())) throw new ApiException("Cannot approve a rejected request");
        if (adv.getRequireApproval() == null || !adv.getRequireApproval()) throw new ApiException("Approval not required for this adventure");

        int active = countActiveParticipants(adv.getId());
        if (active >= adv.getTotalNeeded()) throw new ApiException("Adventure is full");

        String status = req.getStatus();

        if ("APPROVED".equalsIgnoreCase(status)
                || "PAID".equalsIgnoreCase(status)
                || "RELEASED".equalsIgnoreCase(status)
                || "ATTENDED".equalsIgnoreCase(status)) {
            throw new ApiException("Request already active");
        }

        req.setStatus("APPROVED");
        adventureRequestRepository.save(req);
        }





    public void rejectRequest(Integer userId, Integer requestId) {
        AdventureRequest req = adventureRequestRepository.findAdventureRequestById(requestId);
        if (req == null) throw new ApiException("AdventureRequest not found");

        Adventure adv = adventureRepository.findAdventureById(req.getAdventureId());
        if (adv == null) throw new ApiException("Adventure not found");
        if (!adv.getCreatorId().equals(userId)) throw new ApiException("Only the adventure creator can reject this request");

        if ("PAID".equalsIgnoreCase(req.getStatus())) throw new ApiException("Cannot reject a paid request");
        if ("RELEASED".equalsIgnoreCase(req.getStatus())) throw new ApiException("Cannot reject a released request");
        if ("REJECTED".equalsIgnoreCase(req.getStatus())) throw new ApiException("Request already rejected");

        req.setStatus("Rejected");
        adventureRequestRepository.save(req);
    }



    public void pay(Integer userId, Integer requestId) {
        AdventureRequest req = adventureRequestRepository.findAdventureRequestById(requestId);

        if (req == null)
            throw new ApiException("AdventureRequest not found");
        if (!req.getUserId().equals(userId))
            throw new ApiException("Only the requester can pay for this request");

        Adventure adv = adventureRepository.findAdventureById(req.getAdventureId());
        if (adv == null)
            throw new ApiException("Adventure not found");

        String status = req.getStatus();

        if ("REJECTED".equalsIgnoreCase(status))
            throw new ApiException("Rejected request cannot be paid");

        if (adv.getRequireApproval() && !"APPROVED".equalsIgnoreCase(status))
            throw new ApiException("Request must be approved before payment");

        if ("PAID".equalsIgnoreCase(status) || "RELEASED".equalsIgnoreCase(status))
            throw new ApiException("Request is already paid");

        Integer needed = adv.getTotalNeeded();
        if (needed <= 0)
            throw new ApiException("Invalid totalNeeded");

        Double totalPrice = adv.getTotalPrice();
        if (totalPrice < 0)
            throw new ApiException("Invalid totalPrice");

        Double priceForUser = totalPrice / needed;

        User user = userRepository.findUserById(req.getUserId());
        if (user == null)
            throw new ApiException("User not found");

        Double balanceValue = user.getBalance();

        if (balanceValue < priceForUser)
            throw new ApiException("Insufficient balance");

        user.setBalance(balanceValue - priceForUser);
        userRepository.save(user);

        req.setStatus("PAID");
        adventureRequestRepository.save(req);
    }



    public void releasePayment(Integer userId, Integer requestId) {
        AdventureRequest req = adventureRequestRepository.findAdventureRequestById(requestId);
        if (req == null) throw new ApiException("AdventureRequest not found");

        Adventure adv = adventureRepository.findAdventureById(req.getAdventureId());
        if (adv == null) throw new ApiException("Adventure not found");
        if (!adv.getCreatorId().equals(userId)) throw new ApiException("Only the adventure creator can release the payment");

        if (!"PAID".equalsIgnoreCase(req.getStatus())) throw new ApiException("Payment cannot be released before it is paid");

        req.setStatus("Released");
        adventureRequestRepository.save(req);
    }



        public void markAttendance(Integer ownerId, Integer requestId, boolean attended) {
            AdventureRequest req = adventureRequestRepository.findAdventureRequestById(requestId);
            if (req == null) throw new ApiException("AdventureRequest not found");

            Adventure adv = adventureRepository.findAdventureById(req.getAdventureId());
            if (adv == null) throw new ApiException("Adventure not found");
            if (!adv.getCreatorId().equals(ownerId)) throw new ApiException("Only the adventure creator can mark attendance");
            if (adv.getDate().isAfter(LocalDate.now())) throw new ApiException("Cannot mark attendance before the adventure date");

            String status = req.getStatus();

            if (!("APPROVED".equalsIgnoreCase(status)
                    || "PAID".equalsIgnoreCase(status)
                    || "RELEASED".equalsIgnoreCase(status))) {
                throw new ApiException("Only approved or paid requests can be marked attended");
            }

            if (attended) {
                req.setStatus("ATTENDED");
            } else {
                req.setStatus("APPROVED");
            }

            adventureRequestRepository.save(req);
        }

        public void markAttended(Integer ownerId, Integer requestId) {
            markAttendance(ownerId, requestId, true);
        }

        public void markAbsent(Integer ownerId, Integer requestId) {
            markAttendance(ownerId, requestId, false);
        }





        public List<AdventureRequest> getRequestsForAdventureAll(Integer ownerId, Integer adventureId) {
            Adventure adv = adventureRepository.findAdventureById(adventureId);
            if (adv == null) throw new ApiException("Adventure not found");
            if (!adv.getCreatorId().equals(ownerId)) throw new ApiException("You are not allowed to see requests for this adventure");
            return adventureRequestRepository.findAdventureRequestByAdventureId(adventureId);
        }




        public List<AdventureRequest> getRequestsForAdventureByStatus(Integer ownerId, Integer adventureId, String status) {
            Adventure adv = adventureRepository.findAdventureById(adventureId);
            if (adv == null)
                throw new ApiException("Adventure not found");
            if (!adv.getCreatorId().equals(ownerId))
                throw new ApiException("You are not allowed to see requests for this adventure");

            List<AdventureRequest> all = adventureRequestRepository.findAdventureRequestByAdventureId(adventureId);
            if (status == null || status.isBlank()) return all;

            String s = status.trim().toUpperCase();
            List<AdventureRequest> filtered = new ArrayList<>();
            for (AdventureRequest r : all) {
                String rs = r.getStatus() == null ? "" : r.getStatus().toUpperCase();
                switch (s) {
                    case "APPROVED":
                        if ("APPROVED".equals(rs)) filtered.add(r);
                        break;
                    case "PAID":
                        if ("PAID".equals(rs)) filtered.add(r);
                        break;
                    case "RELEASED":
                        if ("RELEASED".equals(rs)) filtered.add(r);
                        break;
                    case "REJECTED":
                        if ("REJECTED".equals(rs)) filtered.add(r);
                        break;
                    case "ATTENDED":
                        if ("ATTENDED".equals(rs)) filtered.add(r);
                        break;
                    case "ACTIVE":
                        if (!"REJECTED".equals(rs)) filtered.add(r);
                        break;
                    default:
                        throw new ApiException("Invalid status filter");
                }
            }
            return filtered;
        }

        private int countActiveParticipants(Integer adventureId) {
            List<AdventureRequest> all = adventureRequestRepository.findAdventureRequestByAdventureId(adventureId);
            int count = 0;
            for (AdventureRequest r : all) {
                String s = r.getStatus();
                if ("APPROVED".equalsIgnoreCase(s)
                        || "PAID".equalsIgnoreCase(s)
                        || "RELEASED".equalsIgnoreCase(s)
                        || "ATTENDED".equalsIgnoreCase(s)) {
                    count++;
                }
            }

            return count;
        }
    }

