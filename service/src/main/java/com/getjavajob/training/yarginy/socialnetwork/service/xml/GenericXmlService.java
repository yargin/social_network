package com.getjavajob.training.yarginy.socialnetwork.service.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class GenericXmlService<E> implements XmlService<E> {
    protected XStream xstream;

    @Autowired
    public void setXstream(XStream xstream) {
        this.xstream = xstream;
    }

    @Override
    public String toXml(E object) {
        return xstream.toXML(object);
    }

    //todo check lost exceptions
    @Override
    public E fromXml(String xml) {
        try {
            initXstream();
            return (E) xstream.fromXML(xml);
        } catch (ConversionException e) {
            throw new IllegalArgumentException(e);
        } catch (StreamException | ClassCastException e) {
            throw new IllegalStateException(e);
        }
    }

    protected abstract void initXstream();
}
