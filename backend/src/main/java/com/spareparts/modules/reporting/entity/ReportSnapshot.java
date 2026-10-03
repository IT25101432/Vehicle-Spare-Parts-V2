package com.spareparts.modules.reporting.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_snapshots")
public class ReportSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "config_id", nullable = false)
    private ReportConfig reportConfig;

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Lob
    @Column(name = "data_payload")
    private String dataPayload;
}
