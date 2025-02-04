package edu.bsu.cs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevisionTest
{
    @Test
    public void testRevisionGetters(){
        //Arrange
        String username="AbcUser0988";
        String timestamp="2025-01-30T12:00;00Z";

        //Act
        WikipediaRevision revision=new WikipediaRevision(username,timestamp);

        //Assert
        assertEquals(username,revision.getUsername());
        assertEquals(timestamp,revision.getTimestamp());
    }

}
