package com.example.rgpdplatform.service;

import com.example.rgpdplatform.dto.GuestDto;
import com.example.rgpdplatform.dto.requests.PaginationDto;
import com.example.rgpdplatform.dto.requests.create.GuestNewDto;
import com.example.rgpdplatform.dto.response.GuestPaginationResultDto;
import com.example.rgpdplatform.dto.response.SuccessDto;

import java.util.List;

public interface IGuestService {

    SuccessDto addGuest(GuestNewDto guestNewDto);

    GuestDto getGuestById(String guestId);

    List<GuestDto> getAllGuests();

    List<GuestDto> getGuestsByOrganisation(String organisationName);

    GuestPaginationResultDto getGuestsByPagination(PaginationDto paginationDto);
}
