 package com.example.rgpdplatform.controller;

import com.example.rgpdplatform.dto.requests.create.OrganisationNewDto;
import com.example.rgpdplatform.dto.OrganisationDto;
import com.example.rgpdplatform.dto.response.SuccessDto;
import com.example.rgpdplatform.service.IOrganisationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("/organisation")
@RequestMapping(path = "/api/organisation")
public class OrganisationController {
    private final IOrganisationService iOrganisationService;

    public OrganisationController(@Qualifier("organisationServiceImpl") IOrganisationService iOrganisationService) {
        this.iOrganisationService = iOrganisationService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<SuccessDto> addOrganisation(@RequestBody @Valid OrganisationNewDto organisationNewDto) {
        return ResponseEntity.ok(iOrganisationService.addOrganisation(organisationNewDto));
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<SuccessDto> updateOrganisation(@RequestBody @Valid OrganisationDto organisationDto) {
        return ResponseEntity.ok(iOrganisationService.updateOrganisation(organisationDto));
    }

    @DeleteMapping(path = "/{organisation_name}/delete")
    public ResponseEntity<SuccessDto> deleteOrganisation(@PathVariable(name = "organisation_name") String organisation_name) {
        return ResponseEntity.ok(iOrganisationService.deleteOrganisation(organisation_name));
    }

    @GetMapping(path = "/{organisation_name}")
    public ResponseEntity<OrganisationDto> getOrganisationByName(
            @PathVariable(name = "organisation_name") String organisation_name) {
        OrganisationDto organisations = iOrganisationService.getOrganisationByName(organisation_name);
        return ResponseEntity.ok(organisations);
    }
    @GetMapping(path = "/{organisation_id}")
    public ResponseEntity<OrganisationDto> getOrganisationById(
            @PathVariable(name = "organisation_id") String organisation_id) {
        OrganisationDto organisations = iOrganisationService.getOrganisationById(organisation_id);
        return ResponseEntity.ok(organisations);
    }
    @GetMapping(path = "/all")
    public ResponseEntity<List<OrganisationDto>> getOrganisations() {
        List<OrganisationDto> organisations = iOrganisationService.getAllOrganisations();
        return ResponseEntity.ok(organisations);
    }


}

