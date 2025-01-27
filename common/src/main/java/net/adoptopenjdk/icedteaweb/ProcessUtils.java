/* 
Copyright (C) 2013 Red Hat, Inc.

This file is part of IcedTea.

IcedTea is free software; you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software
Foundation, version 2.

IcedTea is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with
IcedTea; see the file COPYING. If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is making a
combined work based on this library. Thus, the terms and conditions of the GNU
General Public License cover the whole combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent modules, and
to copy and distribute the resulting executable under terms of your choice,
provided that you also meet, for each linked independent module, the terms and
conditions of the license of that module. An independent module is a module
which is not derived from or based on this library. If you modify this library,
you may extend this exception to your version of the library, but you are not
obligated to do so. If you do not wish to do so, delete this exception
statement from your version.
*/

package net.adoptopenjdk.icedteaweb;

import net.adoptopenjdk.icedteaweb.logging.Logger;
import net.adoptopenjdk.icedteaweb.logging.LoggerFactory;

import java.util.Objects;

public class ProcessUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessUtils.class);

    /**
     * This should be workaround for https://en.wikipedia.org/wiki/Spurious_wakeup which real can happen in case of processes.
     * See http://mail.openjdk.java.net/pipermail/distro-pkg-dev/2015-June/032350.html thread
     *
     * @param process process to be waited for
     */
    public static void waitForSafely(final Process process) {
        Objects.requireNonNull(process);
        boolean pTerminated = false;
        while (!pTerminated) {
            try {
                process.waitFor();
                pTerminated = true;
            } catch (final InterruptedException e) {
                logInterruptedException(e);
            }
        }
    }

    /**
     * Logs the given InterruptedException to the error log using the default error message.
     *
     * @param e the InterruptedException object that occurred and will be logged
     * @return This method does not have a return value as it logs the InterruptedException.
     */
    private static void logInterruptedException(InterruptedException e) {
        LOG.error(IcedTeaWebConstants.DEFAULT_ERROR_MESSAGE, e);
    }

}
