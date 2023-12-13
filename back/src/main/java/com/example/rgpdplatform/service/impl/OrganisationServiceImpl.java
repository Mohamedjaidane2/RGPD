package com.example.rgpdplatform.service.impl;

import com.example.rgpdplatform.dto.OrganisationDto;
import com.example.rgpdplatform.dto.requests.create.OrganisationNewDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.exception.EntityNotFoundException;
import com.example.rgpdplatform.exception.ErrorCodes;
import com.example.rgpdplatform.exception.InvalidOperationException;
import com.example.rgpdplatform.model.*;
import com.example.rgpdplatform.repository.IGuestRepository;
import com.example.rgpdplatform.repository.IOrganisationRepository;
import com.example.rgpdplatform.service.IOrganisationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.rgpdplatform.utils.SuccessMessages.*;

@Service
public class OrganisationServiceImpl implements IOrganisationService {

    private final IOrganisationRepository iOrganisationRepository;
    private final IGuestRepository iGuestRepository;

    public OrganisationServiceImpl(IOrganisationRepository iOrganisationRepository,
                                   IGuestRepository iGuestRepository) {
        this.iOrganisationRepository = iOrganisationRepository;
        this.iGuestRepository = iGuestRepository;
    }
    @Override
    public SuccessDto addOrganisation(OrganisationNewDto organisationNewDto) { //checked
        Optional<Organisation> optionalOrganisation = iOrganisationRepository.findByName(organisationNewDto.getName()) ;
        if(optionalOrganisation.isPresent()){
            throw new EntityNotFoundException("Organisation already exists!", ErrorCodes.ORGANISATION_NOT_VALID);
        }
        try {
            //Guest guest = new Guest();
            Organisation N_organisation = Organisation.builder()
                    .name(organisationNewDto.getName())
                    .activityArea(organisationNewDto.getActivityArea())
                    .expertiseField(organisationNewDto.getExpertiseField())
                    .workforce(organisationNewDto.getWorkforce())
                    .build();
            iOrganisationRepository.save(N_organisation);
        } catch (InvalidOperationException invalidOperationException) {
            throw new InvalidOperationException("Operation not valid!", ErrorCodes.ORGANISATION_NOT_VALID);
        }
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_CREATED)
                .build();
    }
    @Override
    public SuccessDto updateOrganisation(OrganisationDto organisationDto) { //checked
        Optional<Organisation> optionalOrganisation = iOrganisationRepository.findByName(organisationDto.getName());
        if(optionalOrganisation.isEmpty()){
            throw new EntityNotFoundException("Organisation not found with Name : "+organisationDto.getName(),ErrorCodes.ORGANISATION_NOT_FOUND);
        }
        Organisation organisation = optionalOrganisation.get();
        try {
            organisation.setActivityArea(organisationDto.getActivityArea());
            organisation.setName(organisationDto.getName());
            organisation.setWorkforce(organisationDto.getWorkforce());
            organisation.setExpertiseField(organisationDto.getExpertiseField());
            iOrganisationRepository.save(organisation);
        }   catch (InvalidOperationException invalidOperationException) {
                throw new InvalidOperationException("Organisation can't be saved!", ErrorCodes.ORGANISATION_NOT_VALID);
        }
        return SuccessDto
                .builder()
                .message(SUCCESSFULLY_UPDATED)
                .build();
    }
    @Override
    public SuccessDto deleteOrganisation(String organisation_name) { //checked
        Optional<Organisation> organisationOpt = iOrganisationRepository.findByName(organisation_name);
        if (organisationOpt.isEmpty()) {
            throw new EntityNotFoundException("Organisation not found!", ErrorCodes.ORGANISATION_NOT_FOUND);
        }
        List<Guest> guests = iGuestRepository.findAllByOrganisation(organisationOpt.get());
        if(guests.size() > 0){
            throw new InvalidOperationException("This organisation is related to some of guests",ErrorCodes.ORGANISATION_DELETE_NOT_VALID);
        }
        iOrganisationRepository.deleteByName(organisation_name);
        return SuccessDto
                .builder()
                .message(ORGANISATION_SUCCESSFULLY_DELETED)
                .build();
    }
    @Override
    public OrganisationDto getOrganisationByName(String name) { //checked
        Optional<Organisation> optionalOrganisation = iOrganisationRepository.findByName(name);
        if (optionalOrganisation.isPresent()) {
            return OrganisationDto.customMapping(optionalOrganisation.get());
        } else {
            throw new EntityNotFoundException("Organisation not found with Name : " + name, ErrorCodes.QUESTION_NOT_FOUND);
        }

    }

    @Override
    public OrganisationDto getOrganisationById(String organisationId) {
        Optional<Organisation> optionalOrganisation = iOrganisationRepository.findById(organisationId);
        if (optionalOrganisation.isPresent()) {
            return OrganisationDto.customMapping(optionalOrganisation.get());
        } else {
            throw new EntityNotFoundException("Organisation not found with Id : " + organisationId, ErrorCodes.QUESTION_NOT_FOUND);
        }
    }

    @Override
    public List<OrganisationDto> getAllOrganisations() { //checked
        return iOrganisationRepository.findAll()
                .stream()
                .map(OrganisationDto::customMapping)
                .collect(Collectors.toList());
    }
}


