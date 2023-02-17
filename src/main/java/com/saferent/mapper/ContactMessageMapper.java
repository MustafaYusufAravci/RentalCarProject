package com.saferent.mapper;

import com.saferent.domain.ContactMessage;
import com.saferent.dto.ContactMessageDTO;
import com.saferent.dto.request.ContactMessageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring") // herhangi bir sinif enjekte edip kullanabilirim
public interface ContactMessageMapper {

    //!!! ContactMessage ---> ContactMessageDTO
    ContactMessageDTO contactMessageToDTO(ContactMessage contactMessage);

    //!!! ContactMessageRequest ---> ContactMessage
    @Mapping(target="id", ignore = true) // DTO da id olmadigi icin mappleme yapilmasini belirtiyoruz
    ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest);

    //!!! Lisy<ContactMessage> ---> List<ContactMessageDTO>
    List<ContactMessageDTO> map(List<ContactMessage> contactMessageList); // getAllContactMessage()

}
