/*
 * Copyright (c) 2015, Pierre-Anthony Lemieux (pal@sandflow.com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.sandflow.smpte.mxf;

import com.sandflow.smpte.klv.Group;
import com.sandflow.smpte.klv.Triplet;
import com.sandflow.smpte.util.UL;
import com.sandflow.smpte.util.UUID;
import java.util.Collection;

/**
 * Represents a MXF Set (see SMPTE ST 377-1)
 */
public class Set implements Group{
    private static final UL INSTANCE_UID_ITEM_UL = UL.fromURN("urn:smpte:ul:060e2b34.01010101.01011502.00000000");

    /**
     * Creates an MXF Set from a Group
     * @param group Group from which to create the MXF Set
     * @return MXF Set or null if the Group does not contain an Instance ID property
     */
    static public Set fromGroup(Group group) {
        
        for (Triplet t : group.getItems()) {

            if (INSTANCE_UID_ITEM_UL.equalsIgnoreVersion(t.getKey())) {

                UUID uuid = new UUID(t.getValue());

                return new Set(group, uuid);
            }

        }
        
        return null;
    }

    private final UUID instanceID;
    private final Group group;

    private Set(Group group, UUID instanceID) {
        this.group = group;
        this.instanceID = instanceID;
    }
    
    @Override
    public Collection<Triplet> getItems() {
        return group.getItems();
    }

    @Override
    public UL getKey() {
        return group.getKey();
    }

    /**
     * Returns the Instance ID of the MXF Set
     *
     * @return UUID
     */
    public UUID getInstanceID() {
        return instanceID;
    }

    
        
}
