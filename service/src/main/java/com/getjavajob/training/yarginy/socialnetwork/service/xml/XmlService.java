package com.getjavajob.training.yarginy.socialnetwork.service.xml;

public interface XmlService<E> {
    String toXml(E object);

    /**
     * reads {@link String} and creates new {@link Object} casted to specified class
     *
     * @param xml {@link String} containing xml-represented data
     * @return parsed and casted object
     * @throws IllegalArgumentException if xml data contains object-field mistakes
     * @throws IllegalStateException if xml format is wrong or parsed object is not expected type
     */
    E fromXml(String xml);
}
