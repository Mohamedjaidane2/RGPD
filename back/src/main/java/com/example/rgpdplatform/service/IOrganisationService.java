package com.example.rgpdplatform.service;

import com.example.rgpdplatform.dto.OrganisationDto;
import com.example.rgpdplatform.dto.requests.create.OrganisationNewDto;
import com.example.rgpdplatform.dto.response.SuccessDto;

import java.util.List;

public interface IOrganisationService {

    SuccessDto addOrganisation(OrganisationNewDto organisationNewDto);


    SuccessDto updateOrganisation(OrganisationDto organisationDto);

    SuccessDto deleteOrganisation(String organisationId);

    List<OrganisationDto> getAllOrganisations();
    OrganisationDto getOrganisationByName(String name);


    OrganisationDto getOrganisationById(String organisationId);
}
