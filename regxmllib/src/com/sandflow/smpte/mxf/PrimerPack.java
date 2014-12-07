/*
 * Copyright (c) 2014, Pierre-Anthony Lemieux (pal@sandflow.com)
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
package com.sandflow.smpte.regxml;

import com.sandflow.smpte.klv.exception.KLVException;
import com.sandflow.smpte.klv.KLVInputStream;
import com.sandflow.smpte.klv.LocalSetRegister;
import com.sandflow.smpte.klv.Triplet;
import com.sandflow.smpte.util.UL;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Pierre-Anthony Lemieux (pal@sandflow.com)
 */
public class PrimerPack {

    public static final UL LABEL = new UL(new byte[]{0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0d, 0x01, 0x02, 0x01, 0x01, 0x05, 0x01, 0x00});

    public static LocalSetRegister createLocalSetRegister(Triplet triplet) throws KLVException {

        if (!PrimerPack.LABEL.equalsIgnoreVersion(triplet.getKey())) {
            return null;
        }

        HashMap<Long, UL> reg = new HashMap<>();

        KLVInputStream kis = new KLVInputStream(triplet.getValueAsStream());

        try {

            long itemcount = kis.readUnsignedInt();
            
            long itemlength = kis.readUnsignedInt();

            for (int i = 0; i < itemcount; i++) {

                reg.put((long) kis.readUnsignedShort(), kis.readUL());
            }

        } catch (IOException e) {
            throw new KLVException(e);
        }

        return new LocalSetRegister(reg);
    }
}