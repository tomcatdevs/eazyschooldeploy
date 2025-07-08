package com.eazybytes.example18.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "contact_msg")
@NamedQueries(
        {
                @NamedQuery(name = "Contact.findByStatus_NamedQuery", query = "select c from Contact c where c.status=:status"),
                @NamedQuery(name = "Contact.updateByStatusAndContactId_NamedQuery", query = "UPDATE Contact c set c.status=?1 where c.contactId=?2")
        }
)
//NATIVE QUERIES DOES NOT SUPPORT PAGINATION AND SORTING BY DEFAULT
//WE SHOULD PROVIDE TOTAL NO. OF COUNTS USING ===> [resultSetMapping configurations]
//                                @@NamedNativeQuery(name = "Contact.findByStatus_NamedNativeQuery.count",
//                                query = "select count(*) as cnt from contact_id c where c.status=:status",
//                                resultSetMapping = "SqlResultSetMapping.count")
// counting for pagination and sorting
@SqlResultSetMappings({
        @SqlResultSetMapping(name="SqlResultSetMapping.count",columns = @ColumnResult(name = "cnt") )
})
@NamedNativeQueries(
        {
                @NamedNativeQuery(
                                name="Contact.findByStatus_NamedNativeQuery",
                                query = "select * from contact_msg c where c.status=:status",resultClass = Contact.class),
                @NamedNativeQuery(name = "Contact.findByStatus_NamedNativeQuery.count",
                                query = "select count(*) as cnt from contact_msg c where c.status=:status",
                                resultSetMapping = "SqlResultSetMapping.count"),
                @NamedNativeQuery(name="Contact.updateStatusClosedBy_NamedNative",
                query = "UPDATE Contact_msg c set c.status=?1 where c.contact_id=?2",resultClass = Contact.class)
        }
)
public class Contact extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    //public class Contact {
    @Column(name = "contact_id")
    private int contactId;

    @NotBlank(message = "name must not be blank")
    @Size(message = "name should be of at least 3 characters.")
    private String name;

    @NotBlank(message = "mobile number must not be blank")
    @Pattern(regexp = "^$|[0-9]{10}",message = "mobile should be of 10 digits only.")
//    @JsonIgnore
    private String mobileNum;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be in right format")
//    @JsonIgnore
    private String email;

    @NotBlank(message = "subject must not be blank")
    @Size(message = "subject should be of at least 3 characters.")
    private String subject;

    @NotBlank(message = "message must not be blank")
    @Size(message = "message should be of at least 3 characters.")
    private String message;

    private String status;
}
