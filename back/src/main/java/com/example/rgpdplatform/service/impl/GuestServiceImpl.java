package com.example.rgpdplatform.service.impl;

import com.example.rgpdplatform.dto.GuestDto;
import com.example.rgpdplatform.dto.TestDto;
import com.example.rgpdplatform.dto.requests.PaginationDto;
import com.example.rgpdplatform.dto.requests.create.GuestNewDto;
import com.example.rgpdplatform.dto.response.GuestPaginationResultDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.dto.response.TestPaginationResultDto;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.exception.InvalidOperationException;
import com.example.rgpdplatform.model.*;
import com.example.rgpdplatform.repository.*;
import com.example.rgpdplatform.service.IGuestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rgpdplatform.utils.SuccessMessages.*;

@Service
public class GuestServiceImpl implements IGuestService {

    private final IGuestRepository iGuestRepository;
    private final ITestRepository iTestRepository;
    private final IOrganisationRepository iOrganisationRepository;
    private final IUserRepository userRepository;



    public GuestServiceImpl(IGuestRepository iGuestRepository , ITestRepository iTestRepository , IOrganisationRepository iOrganisationRepository, IUserRepository userRepository) {
        this.iGuestRepository = iGuestRepository;
        this.iTestRepository= iTestRepository;
        this.iOrganisationRepository=iOrganisationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SuccessDto addGuest(GuestNewDto guestNewDto) { //checked
            Test test = Test.builder()
                    .score(0F)
                    .refT(guestNewDto.getEmail())
                    .build();
            Optional<Guest> guestOptional = iGuestRepository.findGuestByEmail(guestNewDto.getEmail());
            if (guestOptional.isPresent()) {
                /*
                List<Test> oldTests = iTestRepository.findByGuest_Id(guestOptional.get().getId());
                if(!oldTests.isEmpty() && oldTests.size() >= 3){
                    throw new EntityNotFoundException("Vous avez dépassé le nombre maximal de tests!",ErrorCodes.TEST_EXCEEDED);
                }*/
                Guest guestToUpdate = iGuestRepository.save(guestOptional.get());
                test.setGuest(guestToUpdate);

                Test testCreated = iTestRepository.save(test);
                return SuccessDto
                        .builder()
                        .message(testCreated.getId())
                        .build();
            }else {
                Optional<Organisation> optionalOrganisation = iOrganisationRepository.findByName(guestNewDto.getOrganisation_name());
                if (optionalOrganisation.isEmpty()) {
                    throw new EntityNotFoundException("Organisation Not Found", ErrorCodes.ORGANISATION_NOT_FOUND);
                }
                Guest guestToAdd = new  Guest();

                guestToAdd.setOrganisation(optionalOrganisation.get());
                guestToAdd.setEmail(guestNewDto.getEmail());
                guestToAdd.setExpertiseField(guestNewDto.getExpertiseField());
                guestToAdd.setFirstName(guestNewDto.getFirstName());
                guestToAdd.setLastName(guestNewDto.getLastName());
                guestToAdd.setFunction(guestNewDto.getFunction());
                guestToAdd.setProfile(guestNewDto.getProfile());
                guestToAdd.setTelephone(guestNewDto.getTelephone());

                Guest guestAdded = iGuestRepository.save(guestToAdd);
                test.setGuest(guestAdded);
                test.setRefT(guestAdded.getEmail());
                Test testCreated = iTestRepository.save(test);
                return SuccessDto
                        .builder()
                        .message(testCreated.getId())
                        .build();
            }

        }


    @Override
    public GuestDto getGuestById(String guestId) { //checked
        Optional<Guest> guestOptional = iGuestRepository.findById(guestId);
        if (guestOptional.isEmpty()) {
            throw new EntityNotFoundException("Guest not found", ErrorCodes.GUEST_NOT_FOUND);
        }
        return GuestDto.customMapping(guestOptional.get());
    }

    @Override
    public List<GuestDto> getAllGuests() { //checked

        return iGuestRepository.findAll()
                .stream()
                .map(GuestDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public List<GuestDto> getGuestsByOrganisation(String name) { //checked
        Optional<Organisation> optionalOrganisation = iOrganisationRepository.findByName(name);
        if (optionalOrganisation.isEmpty()){
            throw new EntityNotFoundException("Organisation not found with name : " + name, ErrorCodes.ORGANISATION_NOT_FOUND);
        }
        return iGuestRepository.findAllByOrganisation(optionalOrganisation.get())
                .stream()
                .map(GuestDto::customMapping)
                .collect(Collectors.toList());
    }

    @Override
    public GuestPaginationResultDto getGuestsByPagination(PaginationDto paginationDto) {
        List<Guest> guests = iGuestRepository.findAll();
        GuestPaginationResultDto result = GuestPaginationResultDto
                .builder()
                .count(guests.size())
                .build();
        result.setGuests(guests.stream()
                .skip((long) (paginationDto.getPageNumber() - 1) * paginationDto.getPageSize())
                .limit(paginationDto.getPageSize())
                .map(GuestDto::customMapping)
                .collect(Collectors.toList())
        );
        return result;
    }
}


