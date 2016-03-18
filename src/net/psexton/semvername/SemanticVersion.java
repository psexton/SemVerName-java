/*
 * SemanticVersion.java, part of the semvername-java project
 * Created on Feb 1, 2016, 5:33:02 PM
 *
 * semvername-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * semvername-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with semvername-java. If not, see <http://www.gnu.org/licenses/>.
 */
package net.psexton.semvername;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SemanticVersion.
 * Implements a Semantic Versioning 1.0.0 version number.
 * Immutable class.
 * @author PSexton
 */
public class SemanticVersion implements Comparable<SemanticVersion> {
    private final Integer major;
    private final Integer minor;
    private final Integer patch;
    private final String prerelease;
    private final String build;
    private final ArrayList<Object> prereleaseIds;
    private final ArrayList<Boolean> idsAreNumeric;

    public static SemanticVersion valueOf(String semVerString) {
        if(semVerString == null || semVerString.isEmpty())
            throw new IllegalArgumentException("string cannot be null or empty");
        
        // First use regex to break into major/minor/patch/prerelease/build parts
        // Parsing the prerelease and build strings is done by the constructor
        Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(-([a-zA-Z0-9\\.-]+))?(\\+([a-zA-Z0-9\\.-]+))?");
        Matcher matcher = pattern.matcher(semVerString);
        if(!matcher.matches())
            throw new IllegalArgumentException("string did not match regex");
        
        String majorCapture = matcher.group(1);
        String minorCapture = matcher.group(2);
        String patchCapture = matcher.group(3);
        String prereleaseCapture = matcher.group(5); // group 4 includes the hyphen
        String buildCapture = matcher.group(7); // group 6 includes the plus sign
        
        // Check that major, minor, and patch Captures don't start with a 0 unless they are 0.
        // Integer.valueOf would handle it, but no leading zeros is a requirement of semver2
        if(majorCapture.startsWith("0") && !majorCapture.equals("0"))
            throw new IllegalArgumentException("major cannot have a leading zero");
        if(minorCapture.startsWith("0") && !minorCapture.equals("0"))
            throw new IllegalArgumentException("minor cannot have a leading zero");
        if(patchCapture.startsWith("0") && !patchCapture.equals("0"))
            throw new IllegalArgumentException("patch cannot have a leading zero");
        
        // If there was no prerelease, prereleaseCapture is null
        if(prereleaseCapture == null)
            prereleaseCapture = "";
        
        // If there was no build, buildCapture is null
        if(buildCapture == null)
            buildCapture = "";
        
        return new SemanticVersion(Integer.valueOf(majorCapture), Integer.valueOf(minorCapture), Integer.valueOf(patchCapture), prereleaseCapture, buildCapture);
    }
    
    /**
     * Returns an "empty" semver of 0.0.0.
     */
    public SemanticVersion() {
        this(0, 0, 0);
    }
    
    public SemanticVersion(Integer major, Integer minor, Integer patch) {
        this(major, minor, patch, "");
    }
    
    public SemanticVersion(Integer major, Integer minor, Integer patch, String prerelease) {
        this(major, minor, patch, prerelease, "");
    }
    
    public SemanticVersion(Integer major, Integer minor, Integer patch, String prerelease, String build) {
        // Validate major
        if(major == null)
            throw new IllegalArgumentException("major value cannot be null");
        if(major < 0)
            throw new IllegalArgumentException("major value must be non-negative");
        this.major = major;
        
        // Validate minor
        if(minor == null)
            throw new IllegalArgumentException("minor value cannot be null");
        if(minor < 0)
            throw new IllegalArgumentException("minor value must be non-negative");
        this.minor = minor;
        
        // Validate patch
        if(patch == null)
            throw new IllegalArgumentException("patch value cannot be null");
        if(patch < 0)
            throw new IllegalArgumentException("patch value must be non-negative");
        this.patch = patch;
        
        // Validate prerelease
        prereleaseIds = new ArrayList<>();
        idsAreNumeric = new ArrayList<>();
        
        if(prerelease == null)
            throw new IllegalArgumentException("prerelease string cannot be null");
        
        if(prerelease.contains(".")){
            // Contains periods, need to parse sections
            Pattern disallowedChars = Pattern.compile("[^a-zA-Z0-9-]");
            String[] identifiers = prerelease.split("\\.");
            // NOTE: This split will silently discard trailing periods. Need to check for that case specially
            if(prerelease.endsWith("."))
                throw new IllegalArgumentException("prerelease identifiers cannot be empty");
            for(String part : identifiers) {
                if(disallowedChars.matcher(part).find())
                    throw new IllegalArgumentException("prerelease string is restricted to dot-separated identifiers containing alphanumerics and hyphens");
                if(part.isEmpty())
                    throw new IllegalArgumentException("prerelease identifiers cannot be empty");
                // Still here? Valid identifier
                if(isNumeric(part)) {
                    idsAreNumeric.add(true);
                    prereleaseIds.add(Integer.parseInt(part));
                }
                else {
                    idsAreNumeric.add(false);
                    prereleaseIds.add(part);
                }
            }
        }
        else {
            // No periods, single section
            Pattern disallowedChars = Pattern.compile("[^a-zA-Z0-9-]");
            if(disallowedChars.matcher(prerelease).find())
                throw new IllegalArgumentException("prerelease string is restricted to dot-separated sections of alphanumerics and hyphens");
            if(isNumeric(prerelease)) {
                idsAreNumeric.add(true);
                prereleaseIds.add(Integer.parseInt(prerelease));
            } 
            else {
                idsAreNumeric.add(false);
                prereleaseIds.add(prerelease);
            }
        }
        
        this.prerelease = prerelease;
        
        // Validate build
        if(build == null)
            throw new IllegalArgumentException("build string cannot be null");
        
        if(build.contains(".")){
            // Contains periods, need to parse sections
            Pattern disallowedChars = Pattern.compile("[^a-zA-Z0-9-]");
            String[] identifiers = build.split("\\.");
            // NOTE: This split will silently discard trailing periods. Need to check for that case specially
            if(build.endsWith("."))
                throw new IllegalArgumentException("build identifiers cannot be empty");
            for(String part : identifiers) {
                if(disallowedChars.matcher(part).find())
                    throw new IllegalArgumentException("build string is restricted to dot-separated identifiers containing alphanumerics and hyphens");
                if(part.isEmpty())
                    throw new IllegalArgumentException("build identifiers cannot be empty");
                // Still here? Valid identifier
            }
        }
        else {
            // No periods, single section
            Pattern disallowedChars = Pattern.compile("[^a-zA-Z0-9-]");
            if(disallowedChars.matcher(build).find())
                throw new IllegalArgumentException("build string is restricted to dot-separated sections of alphanumerics and hyphens");
        }
        
        this.build = build;
    }
    
    private boolean isNumeric(String str) {
        // Empty and null strings are not numeric
        if(str == null || str.isEmpty())
            return false;
        // NOTE: For SemVer, we are only concerned with arabic numerals
        // Zero is numeric, but anything with a leading zero is not
        if(str.startsWith("0"))
            return (str.length() == 1);
        // If we're still here, it's either numeric and > 0, or nonnumeric
        return str.matches("[0-9]+");
    }
    
    @Override
    public int compareTo(SemanticVersion rhs) {
        // First compare majors. If not equal, return that.
        int compareMajors = this.major.compareTo(rhs.major);
        if(compareMajors != 0)
            return compareMajors;
        // Next compare minors. If not equal, return that.
        int compareMinors = this.minor.compareTo(rhs.minor);
        if(compareMinors != 0)
            return compareMinors;
        // Next compare patches. If not equal, return that.
        int comparePatches = this.patch.compareTo(rhs.patch);
        if(comparePatches != 0)
            return comparePatches;
        // Major, minor, and patch are equal. Compare prereleases.
        // A non-empty prelease is less than an empty prerelease
        if(this.prerelease.isEmpty() && !rhs.prerelease.isEmpty())
            return 1;
        if(!this.prerelease.isEmpty() && rhs.prerelease.isEmpty())
            return -1;
        if(this.prerelease.isEmpty() && rhs.prerelease.isEmpty())
            return 0;
        // Major, minor, and patch are equal, and both have non-empty prereleases
        // Use custom comparator
        return comparePrereleases(rhs);
        
        // NOTE: build strings are not used when comparing
    }
    
    // Return < 0 if lhs < rhs. 0 if lhs == rhs. > 0 if lhs > rhs
    private int comparePrereleases(SemanticVersion rhs) {
        // Use the prereleaseIds and idIsNumeric ArrayLists we made in ctr
        // Iterate over pairs of identifiers
        
        for(int i = 0; i < Math.min(this.prereleaseIds.size(), rhs.prereleaseIds.size()); i++) {
            // Check for {both numeric, both alpha, mixed}
            // Only digits is always < alphanumeric
            if(this.idsAreNumeric.get(i) && !rhs.idsAreNumeric.get(i))
                return -1;
            if(!this.idsAreNumeric.get(i) && rhs.idsAreNumeric.get(i))
                return 1;
            // Both only digits, compare numericly
            if(this.idsAreNumeric.get(i) && rhs.idsAreNumeric.get(i)) {
                Integer lhsId = (Integer) this.prereleaseIds.get(i);
                Integer rhsId = (Integer) rhs.prereleaseIds.get(i);
                int comparison = lhsId.compareTo(rhsId);
                if(comparison != 0)
                    return comparison;
            }
            // Both alphanumeric, compare lexicographically
            if(!this.idsAreNumeric.get(i) && !rhs.idsAreNumeric.get(i)) {
                String lhsId = (String) this.prereleaseIds.get(i);
                String rhsId = (String) rhs.prereleaseIds.get(i);
                int comparison = lhsId.compareTo(rhsId);
                if(comparison != 0)
                    return comparison;
            }
            
            // They matched, continue to next loop iteration
        }
        
        // All pairable identifiers matched. 
        // Either lhs or rhs has more identifiers left, and the one with more left is greater
        // Or they have same number of identifiers, and they match.
        Integer lhsSize = this.prereleaseIds.size();
        Integer rhsSize = rhs.prereleaseIds.size();
        return lhsSize.compareTo(rhsSize);
    }
    
    /**
     * Check for Compatibly Greater Than status.
     * @param rhs SemanticVersion to compare to
     * @return True if this is compatibly greather than rhs
     */
    public boolean isCompatiblyGreaterThan(SemanticVersion rhs) {
        // If rhs equals this, always return true
        if(this.equals(rhs))
            return true;
        
        // If rhs.major == 0, always return false
        if(rhs.major.equals(0))
            return false;
        
        // If rhs.major >= 1, return true if this.major == rhs.major && this > rhs
        return (this.major.equals(rhs.major) && this.compareTo(rhs) >= 0);
        
        // NOTE: build strings are not used when comparing
    }
    
    //
    // Subsequent methods are boilerplate
    //
    
    public Integer getMajor() {
        return major;
    }

    public Integer getMinor() {
        return minor;
    }

    public Integer getPatch() {
        return patch;
    }

    public String getPrerelease() {
        return prerelease;
    }
    
    public String getBuild() {
        return build;
    }
    
    public SemanticVersion setMajor(Integer major) {
        return new SemanticVersion(major, minor, patch, prerelease, build);
    }

    public SemanticVersion setMinor(Integer minor) {
        return new SemanticVersion(major, minor, patch, prerelease, build);
    }

    public SemanticVersion setPatch(Integer patch) {
        return new SemanticVersion(major, minor, patch, prerelease, build);
    }

    public SemanticVersion setPrerelease(String prerelease) {
        return new SemanticVersion(major, minor, patch, prerelease, build);
    }
    
    public SemanticVersion setBuild(String build) {
        return new SemanticVersion(major, minor, patch, prerelease, build);
    }

    @Override
    public String toString() {
        String stringForm = major + "." + minor + "." + patch;
        if(!prerelease.isEmpty())
            stringForm = stringForm + "-" + prerelease;
        if(!build.isEmpty())
            stringForm = stringForm + "+" + build;
        return stringForm;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.major);
        hash = 53 * hash + Objects.hashCode(this.minor);
        hash = 53 * hash + Objects.hashCode(this.patch);
        hash = 53 * hash + Objects.hashCode(this.prerelease);
        hash = 53 * hash + Objects.hashCode(this.build);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SemanticVersion other = (SemanticVersion) obj;
        if (!Objects.equals(this.major, other.major)) {
            return false;
        }
        if (!Objects.equals(this.minor, other.minor)) {
            return false;
        }
        if (!Objects.equals(this.patch, other.patch)) {
            return false;
        }
        if (!Objects.equals(this.prerelease, other.prerelease)) {
            return false;
        }
        if (!Objects.equals(this.build, other.build)) {
            return false;
        }
        return true;
    }
    
    /**
     * Shorthand for isCompatiblyGreaterThan
     * @param rhs SemanticVersion to compare to
     * @return True if this is compatibly greather than rhs
     */
    public boolean cgt(SemanticVersion rhs) {
        return isCompatiblyGreaterThan(rhs);
    }
}
