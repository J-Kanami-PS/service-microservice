package org.example.cuidadodemascotas.servicemicroservice.utils;

import org.example.cuidadodemascota.commons.entities.service.ServiceType;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class EntityMapper {

    // ========== GETTERS ==========
    public static Long getEntityId(Object entity) {
        if (entity == null) return null;
        try {
            Method getIdMethod = entity.getClass().getMethod("getId");
            return (Long) getIdMethod.invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getServiceTypeName(ServiceType serviceType) {
        if (serviceType == null) return null;
        try {
            Method getNameMethod = serviceType.getClass().getMethod("getName");
            return (String) getNameMethod.invoke(serviceType);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime getCreatedAt(Object entity) {
        if (entity == null) return null;
        try {
            Method getCreatedAtMethod = entity.getClass().getMethod("getCreatedAt");
            return (LocalDateTime) getCreatedAtMethod.invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime getUpdatedAt(Object entity) {
        if (entity == null) return null;
        try {
            Method getUpdatedAtMethod = entity.getClass().getMethod("getUpdatedAt");
            return (LocalDateTime) getUpdatedAtMethod.invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean getActive(Object entity) {
        if (entity == null) return null;
        try {
            Method getActiveMethod = entity.getClass().getMethod("getActive");
            return (Boolean) getActiveMethod.invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }

    // ========== SETTERS ==========
    public static void setEntityId(Object entity, Long id) {
        if (entity == null || id == null) return;
        try {
            Method setIdMethod = entity.getClass().getMethod("setId", Long.class);
            setIdMethod.invoke(entity, id);
        } catch (Exception e) {
            // Ignorar error
        }
    }

    public static void setServiceTypeName(ServiceType serviceType, String name) {
        if (serviceType == null || name == null) return;
        try {
            Method setNameMethod = serviceType.getClass().getMethod("setName", String.class);
            setNameMethod.invoke(serviceType, name);
        } catch (Exception e) {
            // Ignorar error
        }
    }

    // ========== CONVERSIONES ==========
    public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }
}