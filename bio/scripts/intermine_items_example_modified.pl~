#!/usr/bin/perl

# every perl script should start with these two lines.
use strict;
use warnings;

use InterMine::Item::Document;
use InterMine::Model;

my ( $model_file, $out_file, $in_file) =@ARGV;

#create writing apparatus
my $model = new InterMine::Model(file => $model_file);
my $doc = new InterMine::Item::Document->new(
	model => $model,
	output=> $out_file,
);


my $data_source = 'custom-file';

my $gene = $doc->add_item ("QueryId");
    
my $Qlenght = $doc->add_item ("Qlenght");

my $HitEvalue = $doc->add_item ("HitEvalue");

## read the input file
open(my $input, '<', $in_file) or die "Could not open input file";
for (<$input>){
	chomp;  #chomp entfernt letztes Zeichen.
	my($QueryId,$Qlenght,$HitEvalue,$Hitscore,$HitDesc) =split('/\t/');
	#ToDo create a Data Item for each field
	
};

$doc->close(); # writes the xml
exit(0);

######### helper subroutines:


