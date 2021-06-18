package com.github.langerpeng.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author langer_peng
 */
@Entity
@Table(name = "wx_app_bind_app")
@Data
@Accessors(chain = true)
public class AppBindRelationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "wx_app_from_id")
    private Integer wxAppFromId;

    @Basic
    @Column(name = "wx_app_to_id")
    private Integer wxAppToId;

    @Basic
    @Column(name = "create_user_id")
    private Long createUserId;

    @Basic
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Basic
    @Column(name = "status")
    private Integer status;

}
