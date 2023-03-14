package com.example.chatapplication.model;

import java.sql.Timestamp;

// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_chat_history")
public class ChatHistory {

    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatid;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    private Users sender;

    @JoinColumn(name = "receiver_id")
    @ManyToOne
    private Users receiver;

    @Column(name = "message")
    private String message;

    // @CreationTimestamp
    @Column(name = "created_Time")
    private Timestamp createdDate;

    // @UpdateTimestamp
    @Column(name = "updated_Date")
    private Timestamp updatedDate;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status statusId;

}
